package dns.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

import dns.message.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForwardHandler implements Handler {

	private final InetSocketAddress serverAddress;

	@Override
	public Message handle(Message request) throws IOException {
		final var bytes = new byte[1024];

		try (final var socket = new DatagramSocket()) {
			final var forwardBuffer = ByteBuffer.wrap(bytes);
			request.encode(forwardBuffer);

			var packet = new DatagramPacket(bytes, 0, forwardBuffer.position(), serverAddress.getAddress(), serverAddress.getPort());
			socket.send(packet);

			Arrays.fill(bytes, (byte) 0);
			packet = new DatagramPacket(bytes, bytes.length);
			socket.receive(packet);

			final var responseBuffer = ByteBuffer.wrap(packet.getData(), packet.getOffset(), packet.getLength());
			final var response = Message.parse(responseBuffer);

			return response;
		}
	}

}