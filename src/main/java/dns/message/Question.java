package dns.message;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

public record Question(
	/* A domain name, represented as a sequence of "labels" */
	List<String> name,

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

	public static Question parse(DataInputStream dataInputStream) throws IOException {
		final var name = QuestionDecoder.name(dataInputStream);
		final var type = QuestionDecoder.type(dataInputStream);
		final var class_ = QuestionDecoder.class_(dataInputStream);

		return new Question(
			name,
			type,
			class_
		);
	}

}