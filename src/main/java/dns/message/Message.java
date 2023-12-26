package dns.message;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public record Message(
	Header header,
	List<Question> questions,
	List<Answer> answers
) {

	public void encode(ByteBuffer buffer) {
		header.encode(buffer);

		for (final var question : questions) {
			question.encode(buffer);
		}

		for (final var answer : answers) {
			answer.encode(buffer);
		}
	}

	public static Message parse(ByteBuffer buffer) {
		final var header = Header.parse(buffer);

		final var questions = new ArrayList<Question>();
		for (var index = 0; index < header.questionCount(); ++index) {
			questions.add(Question.parse(buffer));
		}

		final var answers = new ArrayList<Answer>();
		for (var index = 0; index < header.answerRecordCount(); ++index) {
			answers.add(Answer.parse(buffer));
		}

		return new Message(header, questions, answers);
	}

}