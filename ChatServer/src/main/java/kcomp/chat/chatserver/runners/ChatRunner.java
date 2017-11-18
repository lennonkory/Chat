package kcomp.chat.chatserver.runners;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;

import kcomp.chat.common.enums.MessageType;
import kcomp.chat.common.messages.Message;

@Component
public class ChatRunner implements CommandLineRunner {

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;

	@Override
	public void run(String... arg0) throws Exception {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Runner >> ");
			System.out.flush();
			String line = in.readLine();
			if (line == null) {
				break;
			}
			if (line.length() == 0) {
				continue;
			}

			System.out.print("Enter ID >> ");
			System.out.flush();

			String id = in.readLine();
			if (id == null) {
				break;
			}
			if (id.length() == 0) {
				continue;
			}

			Message<String> msg = new Message<>("Runner", MessageType.GENERAL, line);

			SimpMessageHeaderAccessor header = createMessage(id);

			messagingTemplate.convertAndSendToUser(id, "/queue/room", msg, header.getMessageHeaders());

		}

	}

	private SimpMessageHeaderAccessor createMessage(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor;
	}

}
