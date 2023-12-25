package dns.util;

public class EncoderHelper {

	public static void addShort(byte[] array, int offset, short value) {
		array[offset + 0] = (byte) (value >> 8);
		array[offset + 1] = (byte) (value);
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