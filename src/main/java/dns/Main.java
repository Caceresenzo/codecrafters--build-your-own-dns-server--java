package dns;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

import dns.handler.EchoHandler;
import dns.handler.ForwardHandler;
import dns.handler.Handler;
import dns.message.Message;

public class Main {

	public static final int PORT = 2053;

	public static void main(String[] args) throws IOException {
		System.out.println("codecrafters build-your-own-dns");
		
		if (args.length == 2 && "--resolver".equals(args[0])) {
			final var parts = args[1].split(":", 2);
			
			final var address = InetAddress.getByName(parts[0]);
			final var port = Integer.parseInt(parts[1]);
			
			serve(new ForwardHandler(new InetSocketAddress(address, port)));
		} else {
			serve(new EchoHandler());
		}
	}

	public static void serve(Handler handler) throws IOException {
		System.out.println("handler: %s".formatted(handler.getClass()));
		
		try (final var serverSocket = new DatagramSocket(PORT)) {
			final var bytes = new byte[1024];

			while (true) {
				final var requestPacket = new DatagramPacket(bytes, bytes.length);
				serverSocket.receive(requestPacket);

				System.out.println("Received data");

				final var requestBuffer = ByteBuffer.wrap(requestPacket.getData(), requestPacket.getOffset(), requestPacket.getLength());

				final var request = Message.parse(requestBuffer);
				final var response = handler.handle(request);

				Arrays.fill(bytes, (byte) 0);
				final var responseBuffer = ByteBuffer.wrap(bytes);
				response.encode(responseBuffer);

				final var responsePacket = new DatagramPacket(bytes, responseBuffer.position(), requestPacket.getSocketAddress());
				serverSocket.send(responsePacket);
			}
		}
	}

}