package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class AnswerTest {

	@Test
	void encode() {
		final var expected = new byte[] {
			6, 'g', 'o', 'o', 'g', 'l', 'e',
			3, 'c', 'o', 'm',
			0,
			(byte) 0b11111111, (byte) 0b11111111,
			(byte) 0b11111111, (byte) 0b11111111,
			(byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111,
			0, 4,
			42, 42, 42, 42
		};

		final var answer = new Answer(
			QuestionEncoderTest.google,
			(byte) -1,
			(byte) -1,
			-1,
			(byte) 4,
			new byte[] { 42, 42, 42, 42 }
		);

		assertArrayEquals(expected, answer.encode());
	}

}