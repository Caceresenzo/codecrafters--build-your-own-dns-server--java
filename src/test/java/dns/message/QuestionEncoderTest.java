package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class QuestionEncoderTest {

	static List<String> google = List.of("google", "com");

	@Test
	void nameSize() {
		assertEquals(12, QuestionEncoder.nameSize(google));
	}

	@Test
	void name() {
		final var expected = new byte[] {
			6, 'g', 'o', 'o', 'g', 'l', 'e',
			3, 'c', 'o', 'm',
			0
		};

		final var bytes = new byte[expected.length];
		QuestionEncoder.name(bytes, google);

		assertArrayEquals(expected, bytes);
	}

	@Test
	void type() {
		final var expected = new byte[] {
			1, 'x', 0,
			(byte) 0b11111111, (byte) 0b11111111
		};

		final var bytes = new byte[expected.length];
		System.arraycopy(expected, 0, bytes, 0, 3);

		QuestionEncoder.type(bytes, 3, (byte) -1);

		assertArrayEquals(expected, bytes);
	}

	@Test
	void class_() {
		final var expected = new byte[] {
			1, 'x', 0,
			0, 0,
			(byte) 0b11111111, (byte) 0b11111111
		};

		final var bytes = new byte[expected.length];
		System.arraycopy(expected, 0, bytes, 0, 3);

		QuestionEncoder.class_(bytes, 3, (byte) -1);

		assertArrayEquals(expected, bytes);
	}

}