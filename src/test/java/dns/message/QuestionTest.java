package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class QuestionTest {

	@Test
	void encode() {
		final var expected = new byte[] {
			6, 'g', 'o', 'o', 'g', 'l', 'e',
			3, 'c', 'o', 'm',
			0,
			(byte) 0b11111111, (byte) 0b11111111,
			(byte) 0b11111111, (byte) 0b11111111,
		};

		final var question = new Question(
			QuestionEncoderTest.google,
			(byte) -1,
			(byte) -1
		);

		assertArrayEquals(expected, question.encode());
	}

}