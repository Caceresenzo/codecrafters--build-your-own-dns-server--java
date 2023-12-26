package dns.message;

import java.nio.ByteBuffer;
import java.util.List;

import dns.util.DecoderHelper;
import dns.util.EncoderHelper;

public record Answer(
	/* Label Sequence	The domain name encoded as a sequence of labels. */
	List<String> name,

	/** 2-byte Integer	1 for an A record, 5 for a CNAME record etc.) */
	short type,

	/** 2-byte Integer	Usually set to 1 */
	short class_,

	/** 4-byte Integer	The duration in seconds a record can be cached before requerying. */
	int timeToLive,

	/** 2-byte Integer	Length of the RDATA field in bytes. */
	short length,

	/** Variable	Data specific to the record type. */
	List<Byte> data
) {

	public void encode(ByteBuffer buffer) {
		EncoderHelper.name(buffer, name);
		buffer.putShort(type);
		buffer.putShort(class_);
		buffer.putInt(timeToLive);
		buffer.putShort(length);
		EncoderHelper.data(buffer, data);
	}

	public static Answer parse(ByteBuffer buffer) {
		final var name = DecoderHelper.name(buffer);
		final var type = buffer.getShort();
		final var class_ = buffer.getShort();
		final var timeToLive = buffer.getInt();
		final var length = buffer.getShort();
		final var data = DecoderHelper.data(buffer, length);

		return new Answer(
			name,
			type,
			class_,
			timeToLive,
			length,
			data
		);
	}

}