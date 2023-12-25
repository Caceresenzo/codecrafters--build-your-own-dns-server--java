package dns.message;

public record Question(
	/* A domain name, represented as a sequence of "labels" */
	String[] name,

	/** 2-byte int; the type of record (1 for an A record, 5 for a CNAME record etc.) */
	short type,

	/** 2-byte int; usually set to 1 */
	short class_
) {

	public byte[] encode() {
		final var nameSize = QuestionEncoder.nameSize(name);

		final var bytes = new byte[nameSize + 4];

		QuestionEncoder.name(bytes, name);
		QuestionEncoder.type(bytes, nameSize, type);
		QuestionEncoder.class_(bytes, nameSize, class_);

		return bytes;
	}

}