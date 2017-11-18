package kcomp.chat.gui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("kcomp")
public class GuiStart {

	public static void main(String[] args) {

		new SpringApplicationBuilder(GuiStart.class).headless(false).web(false).run(args);
	}

}
