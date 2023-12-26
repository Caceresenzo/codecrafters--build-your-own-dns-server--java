package dns.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dns.message.Answer;
import dns.message.Header;
import dns.message.Message;
import dns.message.Question;

public class EchoHandler implements Handler {

	@Override
	public Message handle(Message request) throws IOException {
		final var requestHeader = request.header();
		final var requestQuestions = request.questions();

		final var header = new Header(
			requestHeader.packetIdentifier(),
			true,
			requestHeader.operationCode(),
			false,
			false,
			requestHeader.recursionDesired(),
			false,
			(byte) 0,
			(byte) (requestHeader.operationCode() == 0 ? 0 : 4),
			(byte) requestQuestions.size(),
			(byte) requestQuestions.size(),
			(byte) 0,
			(byte) 0
		);

		final var questions = new ArrayList<Question>();
		final var answers = new ArrayList<Answer>();

		for (final var question : requestQuestions) {
			final var name = question.name();

			questions.add(
				new Question(
					name,
					(short) 1,
					(short) 1
				)
			);

			answers.add(
				new Answer(
					name,
					(short) 1,
					(short) 1,
					60,
					(byte) 4,
					List.of((byte) 8, (byte) 8, (byte) 8, (byte) 8)
				)
			);
		}

		return new Message(
			header,
			questions,
			answers
		);
	}

}