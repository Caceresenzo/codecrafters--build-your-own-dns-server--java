package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class AnswerTest {

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
		AnswerEncoderTest.google,
		(byte) -1,
		(byte) -1,
		-1,
		(byte) 4,
		List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8)
	);

	@Test
	void encode() {
		assertArrayEquals(dummyBytes, dummy.encode());
	}

	@Test
	void parse() throws IOException {
		final var dataInputStream = new DataInputStream(new ByteArrayInputStream(dummyBytes));
		final var parsed = Answer.parse(dataInputStream);

		assertEquals(dummy, parsed);
	}

}