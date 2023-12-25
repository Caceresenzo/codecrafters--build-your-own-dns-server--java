package dns.message;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

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
		assertArrayEquals(dummyBytes, dummy.encode());
	}

	@Test
	void parse() throws IOException {
		final var dataInputStream = new DataInputStream(new ByteArrayInputStream(dummyBytes));
		final var parsed = Header.parse(dataInputStream);
		
		assertEquals(dummy, parsed);
	}

}