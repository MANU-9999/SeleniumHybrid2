package core_configuration.testutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility {
    private LoggerUtility() {
    }

    // Static method to get logger for the provided class
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = null;
        if (logger == null) {
            logger = LogManager.getLogger(clazz);
        }
        return logger;
    }
}

