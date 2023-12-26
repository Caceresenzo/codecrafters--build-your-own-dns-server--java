package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class HeaderTest {

	static byte[] dummyBytes = {
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11001111,
		(byte) 0b11011001,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111,
		(byte) 0b11111111
	};

	static Header dummy = new Header(
		(short) -1,
		true,
		(byte) 0b1001,
		true,
		true,
		true,
		true,
		(byte) 0b101,
		(byte) 0b1001,
		(short) -1,
		(short) -1,
		(short) -1,
		(short) -1
	);

	@Test
	void encode() {
		final var buffer = ByteBuffer.allocate(512);
		dummy.encode(buffer);

		final var bytes = new byte[buffer.position()];
		buffer.rewind().get(bytes);

		assertArrayEquals(dummyBytes, bytes);
	}

	@Test
	void parse() throws IOException {
		final var buffer = ByteBuffer.wrap(dummyBytes);
		final var parsed = Header.parse(buffer);

		assertEquals(dummy, parsed);
		assertFalse(buffer.hasRemaining());
	}

}