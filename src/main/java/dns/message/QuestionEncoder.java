package dns.message;

import dns.util.EncoderHelper;

public class QuestionEncoder {

	public static int nameSize(String[] name) {
		var length = 1 /* null terminator */;

		for (final var label : name) {
			length += 1 /* label length */;
			length += label.length();
		}

		return length;
	}

	public static void name(byte[] bytes, String[] value) {
		var index = 0;
		for (final var label : value) {
			final var labelBytes = label.getBytes();
			final var labelLength = labelBytes.length;

			bytes[index++] = (byte) labelLength;
			System.arraycopy(labelBytes, 0, bytes, index, labelLength);
			index += labelLength;
		}

		bytes[index] = 0;
	}

	public static void type(byte[] bytes, int nameSize, short value) {
		EncoderHelper.addShort(bytes, nameSize, value);
	}

	public static void class_(byte[] bytes, int nameSize, short value) {
		EncoderHelper.addShort(bytes, nameSize + 2, value);
	}

}