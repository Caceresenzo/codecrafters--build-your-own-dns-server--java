package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class AnswerEncoderTest {

	static List<String> google = List.of("google", "com");

	@Test
	void nameSize() {
		assertEquals(12, AnswerEncoder.nameSize(google));
	}

	@Test
	void name() {
		final var expected = new byte[] {
			6, 'g', 'o', 'o', 'g', 'l', 'e',
			3, 'c', 'o', 'm',
			0
		};

		final var bytes = new byte[expected.length];
		AnswerEncoder.name(bytes, google);

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

		AnswerEncoder.type(bytes, 3, (byte) -1);

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

		AnswerEncoder.class_(bytes, 3, (byte) -1);

		assertArrayEquals(expected, bytes);
	}

	@Test
	void timeToLive() {
		final var expected = new byte[] {
			1, 'x', 0,
			0, 0,
			0, 0,
			(byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111, (byte) 0b11111111
		};

		final var bytes = new byte[expected.length];
		System.arraycopy(expected, 0, bytes, 0, 3);

		AnswerEncoder.timeToLive(bytes, 3, (int) -1);

		assertArrayEquals(expected, bytes);
	}

	@Test
	void length() {
		final var expected = new byte[] {
			1, 'x', 0,
			0, 0,
			0, 0,
			0, 0, 0, 0,
			(byte) 0b11111111, (byte) 0b11111111
		};

		final var bytes = new byte[expected.length];
		System.arraycopy(expected, 0, bytes, 0, 3);

		AnswerEncoder.length(bytes, 3, (byte) -1);

		assertArrayEquals(expected, bytes);
	}

	@Test
	void data() {
		final var expected = new byte[] {
			1, 'x', 0,
			0, 0,
			0, 0,
			0, 0, 0, 0,
			0, 0,
			8, 8, 8, 8
		};

		final var bytes = new byte[expected.length];
		System.arraycopy(expected, 0, bytes, 0, 3);

		AnswerEncoder.data(bytes, 3, List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8));

		assertArrayEquals(expected, bytes);
	}

}