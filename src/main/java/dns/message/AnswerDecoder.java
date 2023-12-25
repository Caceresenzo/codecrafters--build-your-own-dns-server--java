package dns.message;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dns.util.DecoderHelper;

public class AnswerDecoder {

	public static List<String> name(DataInputStream dataInputStream) throws IOException {
		return DecoderHelper.name(dataInputStream);
	}

	public static short type(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readShort();
	}

	public static short class_(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readShort();
	}

	public static int timeToLive(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readInt();
	}

	public static short length(DataInputStream dataInputStream) throws IOException {
		return dataInputStream.readShort();
	}

	public static List<Byte> data(DataInputStream dataInputStream, short length) throws IOException {
		final var array = dataInputStream.readNBytes(length);
		final var list = new ArrayList<Byte>(array.length);

		for (byte value : array) {
			list.add(value);
		}

		return list;
	}

}