package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class QuestionTest {

	static byte[] dummyBytes = {
		6, 'g', 'o', 'o', 'g', 'l', 'e',
		3, 'c', 'o', 'm',
		0,
		(byte) 0b11111111, (byte) 0b11111111,
		(byte) 0b11111111, (byte) 0b11111111,
	};

	static Question dummy = new Question(
		QuestionEncoderTest.google,
		(byte) -1,
		(byte) -1
	);

	@Test
	void encode() {
		assertArrayEquals(dummyBytes, dummy.encode());
	}

	@Test
	void parse() throws IOException {
		final var dataInputStream = new DataInputStream(new ByteArrayInputStream(dummyBytes));
		final var parsed = Question.parse(dataInputStream);

		assertEquals(dummy, parsed);
	}

}