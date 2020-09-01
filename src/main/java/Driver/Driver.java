package Driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import MainMenu.MainMenu;

public class Driver {

	static Logger log = LogManager.getLogger(Driver.class);

	
	public static void main(String[] args) {
		Configurator.initialize(null, "log4j.xml");
		log.info("Entering into the MainMenu");
		MainMenu.startMenu();
		log.info("Successfully exited the MainMenu");
	}

}
