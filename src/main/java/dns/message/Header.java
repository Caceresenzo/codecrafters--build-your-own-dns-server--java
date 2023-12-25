package dns.message;

import java.io.DataInputStream;
import java.io.IOException;

public record Header(
	/** 16 bits	A random ID assigned to query packets. Response packets must reply with the same ID. */
	short packetIdentifier,

	/** 1 bit	1 for a reply packet, 0 for a question packet. */
	boolean queryResponseIndicator,

	/** 4 bits	Specifies the kind of query in a message. */
	byte operationCode,

	/** 1 bit	1 if the responding server "owns" the domain queried, i.e., it's authoritative. */
	boolean authoritativeAnswer,

	/** 1 bit	1 if the message is larger than 512 bytes. Always 0 in UDP responses. */
	boolean truncation,

	/** 1 bit	Sender sets this to 1 if the server should recursively resolve this query, 0 otherwise. */
	boolean recursionDesired,

	/** 1 bit	Server sets this to 1 to indicate that recursion is available. */
	boolean recursionAvailable,

	/** 3 bits	Used by DNSSEC queries. At inception, it was reserved for future use. */
	byte reserved,

	/** 4 bits	Response code indicating the status of the response. */
	byte responseCode,

	/** 16 bits	Number of questions in the Question section. */
	short questionCount,

	/** 16 bits	Number of records in the Answer section. */
	short answerRecordCount,

	/** 16 bits	Number of records in the Authority section. */
	short authorityRecordCount,

	/** 16 bits	Number of records in the Additional section. */
	short additionalRecordCount
) {

	public byte[] encode() {
		final var bytes = new byte[12];

		HeaderEncoder.packetIdentifier(bytes, packetIdentifier);

		bytes[2] = (byte) (0
			| (byte) HeaderEncoder.queryResponseIndicator(queryResponseIndicator)
			| (byte) HeaderEncoder.operationCode(operationCode)
			| (byte) HeaderEncoder.authoritativeAnswer(authoritativeAnswer)
			| (byte) HeaderEncoder.truncation(truncation)
			| (byte) HeaderEncoder.recursionDesired(recursionDesired));

		bytes[3] = (byte) (0
			| (byte) HeaderEncoder.recursionAvailable(recursionAvailable)
			| (byte) HeaderEncoder.reserved(reserved)
			| (byte) HeaderEncoder.responseCode(responseCode));

		HeaderEncoder.questionCount(bytes, questionCount);
		HeaderEncoder.answerRecordCount(bytes, answerRecordCount);
		HeaderEncoder.authorityRecordCount(bytes, authorityRecordCount);
		HeaderEncoder.additionalRecordCount(bytes, additionalRecordCount);

		return bytes;
	}

	public static Header parse(DataInputStream dataInputStream) throws IOException {
		final var packetIdentifier = HeaderDecoder.packetIdentifier(dataInputStream);

		final var flags1 = dataInputStream.readByte();
		final var queryResponseIndicator = HeaderDecoder.queryResponseIndicator(flags1);
		final var operationCode = HeaderDecoder.operationCode(flags1);
		final var authoritativeAnswer = HeaderDecoder.authoritativeAnswer(flags1);
		final var truncation = HeaderDecoder.truncation(flags1);
		final var recursionDesired = HeaderDecoder.recursionDesired(flags1);

		final var flags2 = dataInputStream.readByte();
		final var recursionAvailable = HeaderDecoder.recursionAvailable(flags2);
		final var reserved = HeaderDecoder.reserved(flags2);
		final var responseCode = HeaderDecoder.responseCode(flags2);

		final var questionCount = HeaderDecoder.questionCount(dataInputStream);
		final var answerRecordCount = HeaderDecoder.answerRecordCount(dataInputStream);
		final var authorityRecordCount = HeaderDecoder.authorityRecordCount(dataInputStream);
		final var additionalRecordCount = HeaderDecoder.additionalRecordCount(dataInputStream);

		return new Header(
			packetIdentifier,
			queryResponseIndicator,
			operationCode,
			authoritativeAnswer,
			truncation,
			recursionDesired,
			recursionAvailable,
			reserved,
			responseCode,
			questionCount,
			answerRecordCount,
			authorityRecordCount,
			additionalRecordCount
		);
	}

}