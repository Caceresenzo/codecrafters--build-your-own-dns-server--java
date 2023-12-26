package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.junit.jupiter.api.Test;

class AnswerTest {

	static List<String> google = List.of("google", "com");

	static byte[] dummyBytes = {
		6, 'g', 'o', 'o', 'g', 'l', 'e',
		3, 'c', 'o', 'm',
		0,
		(byte) 0b11111111, (byte) 0b11111111,
		(byte) 0b11111111, (byte) 0b11111111,
		(byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111,
		0, 4,
		8, 8, 8, 8
	};

	final Answer dummy = new Answer(
		google,
		(byte) -1,
		(byte) -1,
		-1,
		(byte) 4,
		List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8)
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
		final var parsed = Answer.parse(buffer);

		assertEquals(dummy, parsed);
		assertFalse(buffer.hasRemaining());
	}

}