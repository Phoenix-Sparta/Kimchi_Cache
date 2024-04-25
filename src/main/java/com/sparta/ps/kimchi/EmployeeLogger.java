package com.sparta.ps.kimchi;

import java.io.IOException;
import java.util.logging.*;

public class EmployeeLogger {


    public static void configureLogger(Logger LOGGER) throws IOException {
        LOGGER.setUseParentHandlers(false);


        ConsoleHandler consoleHandler = new ConsoleHandler();
        FileHandler fileHandler = new FileHandler("src/main/resources/logFile.log");

        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new CustomFormatter());

        fileHandler.setLevel(Level.ALL);

        LOGGER.addHandler(consoleHandler);
        LOGGER.addHandler(fileHandler);

        LOGGER.setLevel(Level.ALL);

    }

}
