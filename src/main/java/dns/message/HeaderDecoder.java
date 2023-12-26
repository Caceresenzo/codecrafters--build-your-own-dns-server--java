package dns.message;

import dns.util.DecoderHelper;

public class HeaderDecoder {

	public static boolean queryResponseIndicator(byte flags) {
		return DecoderHelper.flag(flags, 0b10000000);
	}

	public static byte operationCode(byte flags) {
		return DecoderHelper.shift(flags, 0b1111000, 3);
	}

	public static boolean authoritativeAnswer(byte flags) {
		return DecoderHelper.flag(flags, 0b100);
	}

	public static boolean truncation(byte flags) {
		return DecoderHelper.flag(flags, 0b10);
	}

	public static boolean recursionDesired(byte flags) {
		return DecoderHelper.flag(flags, 0b1);
	}

	public static boolean recursionAvailable(byte flags) {
		return DecoderHelper.flag(flags, 0b10000000);
	}

	public static byte reserved(byte value) {
		return DecoderHelper.shift(value, 0b1110000, 4);
	}

	public static byte responseCode(byte value) {
		return DecoderHelper.shift(value, 0b1111, 0);
	}

}