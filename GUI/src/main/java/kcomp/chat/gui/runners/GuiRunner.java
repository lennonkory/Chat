package kcomp.chat.gui.runners;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kcomp.chat.common.entities.Client;
import kcomp.chat.common.entities.StompWebSocketClient;
import kcomp.chat.gui.view.View;

@Component
public class GuiRunner implements CommandLineRunner {

	private static Logger logger = Logger.getLogger(GuiRunner.class.getName());

	@Autowired
	View view;

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("Runner is working");

		view.start();

	}

	public void login() {
		Client client = new StompWebSocketClient();

		client.connect("ws://localhost:8080/lobby");
		client.subscribe("/user/queue/lobby");
		client.send("/app/lobby", "Session ID");

	}

}
