package dns.message;

import java.nio.ByteBuffer;
import java.util.List;

import dns.util.DecoderHelper;

public class QuestionDecoder {

	public static List<String> name(ByteBuffer buffer) {
		return DecoderHelper.name(buffer);
	}

	public static short type(ByteBuffer buffer) {
		return buffer.getShort();
	}

	public static short class_(ByteBuffer buffer) {
		return buffer.getShort();
	}

}