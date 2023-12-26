package dns.message;

import java.nio.ByteBuffer;

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

	public void encode(ByteBuffer buffer) {
		buffer.putShort(packetIdentifier);

		buffer.put((byte) (0
			| (byte) HeaderEncoder.queryResponseIndicator(queryResponseIndicator)
			| (byte) HeaderEncoder.operationCode(operationCode)
			| (byte) HeaderEncoder.authoritativeAnswer(authoritativeAnswer)
			| (byte) HeaderEncoder.truncation(truncation)
			| (byte) HeaderEncoder.recursionDesired(recursionDesired)));

		buffer.put((byte) (0
			| (byte) HeaderEncoder.recursionAvailable(recursionAvailable)
			| (byte) HeaderEncoder.reserved(reserved)
			| (byte) HeaderEncoder.responseCode(responseCode)));

		buffer.putShort(questionCount);
		buffer.putShort(answerRecordCount);
		buffer.putShort(authorityRecordCount);
		buffer.putShort(additionalRecordCount);
	}

	public static Header parse(ByteBuffer buffer) {
		final var packetIdentifier = buffer.getShort();

		final var flags1 = buffer.get();
		final var queryResponseIndicator = HeaderDecoder.queryResponseIndicator(flags1);
		final var operationCode = HeaderDecoder.operationCode(flags1);
		final var authoritativeAnswer = HeaderDecoder.authoritativeAnswer(flags1);
		final var truncation = HeaderDecoder.truncation(flags1);
		final var recursionDesired = HeaderDecoder.recursionDesired(flags1);

		final var flags2 = buffer.get();
		final var recursionAvailable = HeaderDecoder.recursionAvailable(flags2);
		final var reserved = HeaderDecoder.reserved(flags2);
		final var responseCode = HeaderDecoder.responseCode(flags2);

		final var questionCount = buffer.getShort();
		final var answerRecordCount = buffer.getShort();
		final var authorityRecordCount = buffer.getShort();
		final var additionalRecordCount = buffer.getShort();

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