package dns.message;

public record Answer(
	/* Label Sequence	The domain name encoded as a sequence of labels. */
	String[] name,

	/** 2-byte Integer	1 for an A record, 5 for a CNAME record etc.) */
	short type,

	/** 2-byte Integer	Usually set to 1 */
	short class_,

	/** 4-byte Integer	The duration in seconds a record can be cached before requerying. */
	int timeToLive,

	/** 2-byte Integer	Length of the RDATA field in bytes. */
	short length,

	/** Variable	Data specific to the record type. */
	byte[] data
) {

	public byte[] encode() {
		final var nameSize = QuestionEncoder.nameSize(name);

		final var bytes = new byte[nameSize + 10 + length];

		AnswerEncoder.name(bytes, name);
		AnswerEncoder.type(bytes, nameSize, type);
		AnswerEncoder.class_(bytes, nameSize, class_);
		AnswerEncoder.timeToLive(bytes, nameSize, timeToLive);
		AnswerEncoder.length(bytes, nameSize, length);
		AnswerEncoder.data(bytes, nameSize, data);

		return bytes;
	}

}