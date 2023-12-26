package dns.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import dns.message.Answer;
import dns.message.Header;
import dns.message.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForwardHandler implements Handler {

	private final InetSocketAddress serverAddress;

	@Override
	public Message handle(Message request) throws IOException {
		final var requestHeader = request.header();
		final var questions = request.questions();

		if (questions.size() == 1) {
			return forward(request);
		}

		final var answers = new ArrayList<Answer>();

		for (final var question : questions) {
			final var request2 = new Message(
				new Header(
					requestHeader.packetIdentifier(),
					false,
					requestHeader.operationCode(),
					false,
					false,
					requestHeader.recursionDesired(),
					false,
					(byte) 0,
					(byte) (requestHeader.operationCode() == 0 ? 0 : 4),
					(byte) 1,
					(byte) 0,
					(byte) 0,
					(byte) 0
				),
				Collections.singletonList(question),
				Collections.emptyList()
			);

			answers.addAll(forward(request2).answers());
		}

		//		System.out.println(questions);
		//		System.out.println(answers);

		return new Message(
			new Header(
				requestHeader.packetIdentifier(),
				true,
				requestHeader.operationCode(),
				false,
				false,
				requestHeader.recursionDesired(),
				false,
				(byte) 0,
				(byte) (requestHeader.operationCode() == 0 ? 0 : 4),
				(byte) questions.size(),
				(byte) answers.size(),
				(byte) 0,
				(byte) 0
			),
			questions,
			answers
		);
	}

	public Message forward(Message request) throws IOException {
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

			//			System.out.println(response);

			return response;
		}
	}

}