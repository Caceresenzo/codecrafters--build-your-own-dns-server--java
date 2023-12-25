package dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import dns.message.Header;
import dns.message.Message;
import dns.message.Question;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("codecrafters build-your-own-dns");

		try (final var serverSocket = new DatagramSocket(2053)) {
			while (true) {
				final var requestBuffer = new byte[512];
				final var request = new DatagramPacket(requestBuffer, requestBuffer.length);
				serverSocket.receive(request);

				System.out.println("Received data");

				final var header = new Header(
					(short) 1234,
					true,
					(byte) 0,
					false,
					false,
					false,
					false,
					(byte) 0,
					(byte) 0,
					(short) 0,
					(short) 0,
					(short) 0,
					(short) 0
				);

				final var question = new Question(
					new String[] { "codecrafters", "io" },
					(short) 1,
					(short) 1
				);
				
				final var message = new Message(header, question);

				final byte[] responseBuffer = message.encode();
				final var response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getSocketAddress());
				serverSocket.send(response);
			}
		}
	}

}