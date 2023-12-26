package dns.util;

import java.nio.ByteBuffer;
import java.util.List;

public class EncoderHelper {

	public static void name(ByteBuffer buffer, List<String> value) {
		for (final var label : value) {
			final var labelBytes = label.getBytes();

			buffer.put((byte) labelBytes.length);
			buffer.put(labelBytes);
		}

		buffer.put((byte) 0);
	}

	public static void data(ByteBuffer buffer, List<Byte> value) {
		for (final var data : value) {
			buffer.put(data);
		}
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