package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.junit.jupiter.api.Test;

class QuestionTest {

	static List<String> google = List.of("google", "com");

	static byte[] dummyBytes = {
		6, 'g', 'o', 'o', 'g', 'l', 'e',
		3, 'c', 'o', 'm',
		0,
		(byte) 0b11111111, (byte) 0b11111111,
		(byte) 0b11111111, (byte) 0b11111111,
	};

	static Question dummy = new Question(
		google,
		(byte) -1,
		(byte) -1
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
		final var parsed = Question.parse(buffer);

		assertEquals(dummy, parsed);
		assertFalse(buffer.hasRemaining());
	}

}