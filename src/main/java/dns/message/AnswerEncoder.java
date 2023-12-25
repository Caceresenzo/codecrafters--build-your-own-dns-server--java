package dns.message;

import java.util.List;

import dns.util.EncoderHelper;

public class AnswerEncoder {

	public static int nameSize(List<String> name) {
		return EncoderHelper.nameSize(name);
	}

	public static void name(byte[] bytes, List<String> value) {
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

	public static void data(byte[] bytes, int nameSize, List<Byte> value) {
		for (var index = 0; index < value.size(); ++index) {
			bytes[nameSize + 10 + index] = value.get(index);
		}
	}

}