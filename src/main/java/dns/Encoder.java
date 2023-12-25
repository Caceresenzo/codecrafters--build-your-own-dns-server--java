package dns;

import java.util.Arrays;

public class Encoder {

	private static byte shift(boolean value, int shift) {
		if (value) {
			return (byte) (1 << shift);
		}

		return 0;
	}

	private static byte shift(byte value, int mask, int shift) {
		return (byte) ((value & mask) << shift);
	}

	public static void addShort(byte[] array, int offset, short value) {
		array[offset + 0] = (byte) (value >> 8);
		array[offset + 1] = (byte) (value);
	}

	public static byte queryResponseIndicator(boolean value) {
		return shift(value, 7);
	}

	public static byte operationCode(byte value) {
		return shift(value, 0b1111, 3);
	}

	public static byte authoritativeAnswer(boolean value) {
		return shift(value, 2);
	}

	public static byte truncation(boolean value) {
		return shift(value, 1);
	}

	public static byte recursionDesired(boolean value) {
		return shift(value, 0);
	}

	public static byte recursionAvailable(boolean value) {
		return shift(value, 7);
	}

	public static byte reserved(byte value) {
		return shift(value, 0b111, 4);
	}

	public static byte responseCode(byte value) {
		return shift(value, 0b1111, 0);
	}

	public static void main(String[] args) {
		assert queryResponseIndicator(true) == (byte) 0b10000000;

		//		System.out.println(Integer.toBinaryString(Byte.toUnsignedInt(operationCode((byte) 0b1101))));
		assert operationCode((byte) 0b1101) == (byte) 0b1101000;

		assert authoritativeAnswer(true) == (byte) 0b00000100;

		assert truncation(true) == (byte) 0b00000010;

		assert recursionDesired(true) == (byte) 0b00000001;

		assert recursionAvailable(true) == (byte) 0b10000000;

		assert reserved((byte) 0b101) == (byte) 0b01010000;

		assert responseCode((byte) 0b1101) == (byte) 0b00001101;

		final var message = new Message(
			(short) -1,
			true,
			(byte) 0b1001,
			true,
			true,
			true,
			true,
			(byte) 0b101,
			(byte) 0b1001,
			(short) -1,
			(short) -1,
			(short) -1,
			(short) -1
		);

		final var expected = new byte[] {
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11001111,
			(byte) 0b11011001,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111,
			(byte) 0b11111111
		};

		for (final var b : message.encode()) {
			System.out.print(Integer.toBinaryString(Byte.toUnsignedInt(b)));
			System.out.print(' ');
		}
		System.out.println();

		assert Arrays.equals(expected, message.encode());
	}

}