package dns.message;

import java.nio.ByteBuffer;
import java.util.List;

import dns.util.DecoderHelper;
import dns.util.EncoderHelper;

public record Question(
	/* A domain name, represented as a sequence of "labels" */
	List<String> name,

	/** 2-byte int; the type of record (1 for an A record, 5 for a CNAME record etc.) */
	short type,

	/** 2-byte int; usually set to 1 */
	short class_
) {

	public void encode(ByteBuffer buffer) {
		EncoderHelper.name(buffer, name);
		buffer.putShort(type);
		buffer.putShort(class_);
	}

	public static Question parse(ByteBuffer buffer) {
		final var name = DecoderHelper.name(buffer);
		final var type = buffer.getShort();
		final var class_ = buffer.getShort();

		return new Question(
			name,
			type,
			class_
		);
	}

}