package dns.message;

import dns.util.EncoderHelper;

public class HeaderEncoder {

	public static byte queryResponseIndicator(boolean value) {
		return EncoderHelper.shift(value, 7);
	}

	public static byte operationCode(byte value) {
		return EncoderHelper.shift(value, 0b1111, 3);
	}

	public static byte authoritativeAnswer(boolean value) {
		return EncoderHelper.shift(value, 2);
	}

	public static byte truncation(boolean value) {
		return EncoderHelper.shift(value, 1);
	}

	public static byte recursionDesired(boolean value) {
		return EncoderHelper.shift(value, 0);
	}

	public static byte recursionAvailable(boolean value) {
		return EncoderHelper.shift(value, 7);
	}

	public static byte reserved(byte value) {
		return EncoderHelper.shift(value, 0b111, 4);
	}

	public static byte responseCode(byte value) {
		return EncoderHelper.shift(value, 0b1111, 0);
	}

}