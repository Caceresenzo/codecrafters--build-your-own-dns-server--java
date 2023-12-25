package dns;

public record Message(
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

		Encoder.addShort(bytes, 0, packetIdentifier);

		bytes[2] = (byte) (0
			| (byte) Encoder.queryResponseIndicator(queryResponseIndicator)
			| (byte) Encoder.operationCode(operationCode)
			| (byte) Encoder.authoritativeAnswer(authoritativeAnswer)
			| (byte) Encoder.truncation(truncation)
			| (byte) Encoder.recursionDesired(recursionDesired));

		bytes[3] = (byte) (0
			| (byte) Encoder.recursionAvailable(recursionAvailable)
			| (byte) Encoder.reserved(reserved)
			| (byte) Encoder.responseCode(responseCode));

		Encoder.addShort(bytes, 4, questionCount);
		Encoder.addShort(bytes, 6, answerRecordCount);
		Encoder.addShort(bytes, 8, authorityRecordCount);
		Encoder.addShort(bytes, 10, additionalRecordCount);

		return bytes;
	}

}