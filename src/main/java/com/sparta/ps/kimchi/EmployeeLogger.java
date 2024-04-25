package com.sparta.ps.kimchi;

import java.io.IOException;
import java.util.logging.*;

public class EmployeeLogger {


    public static void configureLogger(Logger LOGGER) throws IOException {
        LOGGER.setUseParentHandlers(false);


        ConsoleHandler consoleHandler = new ConsoleHandler();
        FileHandler fileHandler = new FileHandler("src/main/resources/logFile.log");

        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new CustomFormatter());

        fileHandler = new FileHandler("src/main/resources/logFile.log");
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new XMLFormatter());

        LOGGER.addHandler(consoleHandler);
        LOGGER.addHandler(fileHandler);

        LOGGER.setLevel(Level.ALL);

    }

}
