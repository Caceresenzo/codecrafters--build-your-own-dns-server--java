package dns.message;

import dns.util.EncoderHelper;

public class MessageEncoder {

	public static void size(byte[] bytes, short value) {
		EncoderHelper.addShort(bytes, 0, value);
	}

}