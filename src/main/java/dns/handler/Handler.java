package dns.handler;

import java.io.IOException;

import dns.message.Message;

public interface Handler {

	Message handle(Message request) throws IOException;
	
}