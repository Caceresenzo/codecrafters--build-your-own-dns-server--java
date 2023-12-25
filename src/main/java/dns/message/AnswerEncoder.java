package dns.message;

import dns.util.EncoderHelper;

public class AnswerEncoder {

	public static int nameSize(String[] name) {
		return EncoderHelper.nameSize(name);
	}

	public static void name(byte[] bytes, String[] value) {
		EncoderHelper.name(bytes, value);
	}

	public static void type(byte[] bytes, int nameSize, short value) {
		EncoderHelper.addShort(bytes, nameSize, value);
	}

	public static void class_(byte[] bytes, int nameSize, short value) {
		EncoderHelper.addShort(bytes, nameSize + 2, value);
	}

	public static void timeToLive(byte[] bytes, int nameSize, int value) {
		EncoderHelper.addInt(bytes, nameSize + 4, value);
	}

	public static void length(byte[] bytes, int nameSize, short value) {
		EncoderHelper.addShort(bytes, nameSize + 8, value);
	}

	public static void data(byte[] bytes, int nameSize, byte[] value) {
		System.arraycopy(value, 0, bytes, nameSize + 10, value.length);
	}

}