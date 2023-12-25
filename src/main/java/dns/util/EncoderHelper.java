package dns.util;

public class EncoderHelper {

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

	public static void addShort(byte[] array, int offset, short value) {
		array[offset + 0] = (byte) (value >> 8);
		array[offset + 1] = (byte) (value);
	}

	public static void addInt(byte[] array, int offset, int value) {
		array[offset + 0] = (byte) (value >> 24);
		array[offset + 1] = (byte) (value >> 16);
		array[offset + 2] = (byte) (value >> 8);
		array[offset + 3] = (byte) (value);
	}

	public static byte shift(boolean value, int amount) {
		if (value) {
			return (byte) (1 << amount);
		}

		return 0;
	}

	public static byte shift(byte value, int mask, int amount) {
		return (byte) ((value & mask) << amount);
	}

}