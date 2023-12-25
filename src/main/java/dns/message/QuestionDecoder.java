package dns.message;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDecoder {

	public static List<String> name(DataInputStream dataInputStream) throws IOException {
		final var labels = new ArrayList<String>();

		byte length;
		while ((length = dataInputStream.readByte()) != 0) {
			final var label = dataInputStream.readNBytes(length);
			labels.add(new String(label));
		}

		return labels;
	}

	public static short type(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readShort();
	}

	public static short class_(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readShort();
	}

}