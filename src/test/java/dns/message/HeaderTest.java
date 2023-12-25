package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class HeaderTest {

	@Test
	void encode() {
		final var expected = new byte[] {
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

		final var message = new Header(
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

		assertArrayEquals(expected, message.encode());
	}

}