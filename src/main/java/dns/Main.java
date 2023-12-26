package dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dns.message.Answer;
import dns.message.Header;
import dns.message.Message;
import dns.message.Question;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("codecrafters build-your-own-dns");

		try (final var serverSocket = new DatagramSocket(2053)) {
			final var bytes = new byte[1024];

			while (true) {
				final var requestPacket = new DatagramPacket(bytes, bytes.length);
				serverSocket.receive(requestPacket);

				System.out.println("Received data");

				//				System.out.println(new String(bytes, 0, requestPacket.getLength()));
				//				try (final var outputStream = new FileOutputStream(new File("req.bin"))) {
				//					outputStream.write(requestPacket.getData(), requestPacket.getOffset(), requestPacket.getLength());
				//				}

				final var requestBuffer = ByteBuffer.wrap(requestPacket.getData(), requestPacket.getOffset(), requestPacket.getLength());

				final var request = Message.parse(requestBuffer);
				final var response = handle(request);

				Arrays.fill(bytes, (byte) 0);
				final var responseBuffer = ByteBuffer.wrap(bytes);
				response.encode(responseBuffer);

				final var responsePacket = new DatagramPacket(bytes, responseBuffer.position(), requestPacket.getSocketAddress());
				serverSocket.send(responsePacket);
			}
		}
	}

	private static Message handle(Message request) {
		final var requestHeader = request.header();
		final var requestQuestions = request.questions();

		final var header = new Header(
			requestHeader.packetIdentifier(),
			true,
			requestHeader.operationCode(),
			false,
			false,
			requestHeader.recursionDesired(),
			false,
			(byte) 0,
			(byte) (requestHeader.operationCode() == 0 ? 0 : 4),
			(byte) requestQuestions.size(),
			(byte) requestQuestions.size(),
			(byte) 0,
			(byte) 0
		);

		final var questions = new ArrayList<Question>();
		final var answers = new ArrayList<Answer>();

		for (final var question : requestQuestions) {
			final var name = question.name();

			questions.add(
				new Question(
					name,
					(short) 1,
					(short) 1
				)
			);

			answers.add(
				new Answer(
					name,
					(short) 1,
					(short) 1,
					60,
					(byte) 4,
					List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8)
				)
			);
		}

		return new Message(
			header,
			questions,
			answers
		);
	}

}