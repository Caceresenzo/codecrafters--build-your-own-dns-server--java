package dns.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DecoderHelper {

	public static List<String> name(DataInputStream dataInputStream) throws IOException {
		final var labels = new ArrayList<String>();

		byte length;
		while ((length = dataInputStream.readByte()) != 0) {
			final var label = dataInputStream.readNBytes(length);
			labels.add(new String(label));
		}

		return labels;
	}

	public static boolean flag(byte set, int mask) {
		return (set & mask) != 0;
	}

	public static byte shift(byte value, int mask, int amount) {
		return (byte) ((value & mask) >> amount);
	}

}