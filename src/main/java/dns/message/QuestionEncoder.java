package dns.message;

import dns.util.EncoderHelper;

public class QuestionEncoder {

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

}