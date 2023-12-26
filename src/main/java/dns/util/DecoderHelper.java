package dns.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DecoderHelper {

	public static List<String> name(ByteBuffer buffer) {
		final var labels = new ArrayList<String>();

		byte length;
		while ((length = buffer.get()) != 0) {
			final var label = new byte[length];
			buffer.get(label);

			labels.add(new String(label));
		}

		return labels;
	}

	public static List<Byte> data(ByteBuffer buffer, int length) {
		final var list = new ArrayList<Byte>(length);
		
		for (var index = 0; index < length; ++index) {
			list.add(buffer.get());
		}
		
		return list;
	}

	public static boolean flag(byte set, int mask) {
		return (set & mask) != 0;
	}

	public static byte shift(byte value, int mask, int amount) {
		return (byte) ((value & mask) >> amount);
	}

}