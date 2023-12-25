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

				final byte[] responseBuffer = new byte[512];
				final var response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getSocketAddress());
				serverSocket.send(response);
			}
		}
	}

}