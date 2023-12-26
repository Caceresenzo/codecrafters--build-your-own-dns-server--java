package dns.message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HeaderEncoderTest {

	@Test
	void queryResponseIndicator() {
		assertEquals((byte) 0b10000000, HeaderEncoder.queryResponseIndicator(true));
	}

	@Test
	void operationCode() {
		assertEquals((byte) 0b1101000, HeaderEncoder.operationCode((byte) 0b1101));
	}

	@Test
	void authoritativeAnswer() {
		assertEquals((byte) 0b00000100, HeaderEncoder.authoritativeAnswer(true));
	}

	@Test
	void truncation() {
		assertEquals((byte) 0b00000010, HeaderEncoder.truncation(true));
	}

	@Test
	void recursionDesired() {
		assertEquals((byte) 0b00000001, HeaderEncoder.recursionDesired(true));
	}

	@Test
	void recursionAvailable() {
		assertEquals((byte) 0b10000000, HeaderEncoder.recursionAvailable(true));
	}

	@Test
	void reserved() {
		assertEquals((byte) 0b01010000, HeaderEncoder.reserved((byte) 0b101));
	}

	@Test
	void responseCode() {
		assertEquals((byte) 0b00001101, HeaderEncoder.responseCode((byte) 0b1101));
	}

}