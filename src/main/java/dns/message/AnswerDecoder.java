package dns.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import dns.util.DecoderHelper;

public class AnswerDecoder {

	public static List<String> name(ByteBuffer buffer) {
		return DecoderHelper.name(buffer);
	}

	public static short type(ByteBuffer buffer) {
		return buffer.getShort();
	}

	public static short class_(ByteBuffer buffer) {
		return buffer.getShort();
	}

	public static int timeToLive(ByteBuffer buffer) {
		return buffer.getInt();
	}

	public static short length(ByteBuffer buffer) {
		return buffer.getShort();
	}

	public static List<Byte> data(ByteBuffer buffer, short length) {
		final var list = new ArrayList<Byte>(length);

		for (var index = 0; index < length; ++index) {
			list.add(buffer.get());
		}

		return list;
	}

}