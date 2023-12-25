package dns.message;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

public record Message(
	Header header,
	List<Question> questions,
	List<Answer> answers
) {

	@SneakyThrows
	public byte[] encode() {
		final var outputStream = new ByteArrayOutputStream();

		outputStream.write(header.encode());

		for (final var question : questions) {
			outputStream.write(question.encode());
		}

		for (final var answer : answers) {
			outputStream.write(answer.encode());
		}

		return outputStream.toByteArray();
	}

	public static Message parse(InputStream inputStream) throws IOException {
		final var dataInputStream = new DataInputStream(inputStream);

		final var header = Header.parse(dataInputStream);

		final var questions = new ArrayList<Question>();
		for (var index = 0; index < header.questionCount(); ++index) {
			questions.add(Question.parse(dataInputStream));
		}

		final var answers = new ArrayList<Answer>();
		for (var index = 0; index < header.answerRecordCount(); ++index) {
			answers.add(Answer.parse(dataInputStream));
		}

		return new Message(header, questions, answers);
	}

}