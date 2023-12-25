package dns;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("codecrafters build-your-own-dns");

		try (final var serverSocket = new DatagramSocket(2053)) {
			while (true) {
				final var requestBuffer = new byte[512];
				final var request = new DatagramPacket(requestBuffer, requestBuffer.length);
				serverSocket.receive(request);

				System.out.println("Received data");

				final var message = new Message(
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

				final byte[] responseBuffer = message.encode();
				final var response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getSocketAddress());
				serverSocket.send(response);
			}
		}
	}

}