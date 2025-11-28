package com.Project.UPIRecon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingConfig {

    private LoggingConfig() {
        // utility class, no instances
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
