package dns;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import dns.message.Answer;
import dns.message.Header;
import dns.message.Message;
import dns.message.Question;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("codecrafters build-your-own-dns");

		try (final var serverSocket = new DatagramSocket(2053)) {
			while (true) {
				final var requestBuffer = new byte[512];
				final var requestPacket = new DatagramPacket(requestBuffer, requestBuffer.length);
				serverSocket.receive(requestPacket);

				System.out.println("Received data");

				final var inputStream = new ByteArrayInputStream(requestPacket.getData(), 0, requestPacket.getLength());

				final var request = Message.parse(inputStream);
				final var response = handle(request);

				final byte[] responseBuffer = response.encode();
				final var responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, requestPacket.getSocketAddress());
				serverSocket.send(responsePacket);
			}
		}
	}

	private static Message handle(Message request) {
		final var requestHeader = request.header();

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
			(byte) 1,
			(byte) 1,
			(byte) 0,
			(byte) 0
		);

		final var questions = List.of(
			new Question(
				List.of("codecrafters", "io"),
				(short) 1,
				(short) 1
			)
		);

		final var answers = List.of(
			new Answer(
				List.of("codecrafters", "io"),
				(short) 1,
				(short) 1,
				60,
				(byte) 4,
				List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8)
			)
		);

		return new Message(
			header,
			questions,
			answers
		);
	}

}