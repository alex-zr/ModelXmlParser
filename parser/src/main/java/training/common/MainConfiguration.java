package training.common;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: al1
 * Date: 01.03.12
 */
public class MainConfiguration {
    private final String CONF_FILE_NAME = "config.properties";
    private final String INPUT_PATH = "input.path";
    private final String OUTPUT_PATH = "output.path";
    private final String MODEL_JAR_NAME = "jar.name";

    private static String input;
    private static String output;
    private static String jarName;

    private static boolean initiated = false;

    public void load() {
        Properties prop = new Properties();

        try {
            prop.load(this.getClass().getResourceAsStream(CONF_FILE_NAME));

            input = prop.getProperty(INPUT_PATH);
            output = prop.getProperty(OUTPUT_PATH);
            jarName = prop.getProperty(MODEL_JAR_NAME);

            if(input == null || input.isEmpty()) {
                throw new LogicException(INPUT_PATH + " parameter is absent in config");
            }
            if(output == null || input.isEmpty()) {
                throw new LogicException(OUTPUT_PATH + " parameter is absent in config");
            }

            if(jarName == null || input.isEmpty()) {
                throw new LogicException(MODEL_JAR_NAME + "parameter is absent in config");
            }

            initiated = true;
        } catch (IOException e) {
            throw new LogicException("Read configuration file error [" + CONF_FILE_NAME + "] " + e);
        }
    }

    public static String getInput() {
        ensureInit();
        return input;
    }

    public static String getOutput() {
        ensureInit();
        return output;
    }

    public static String getJarName() {
        ensureInit();
        return jarName;
    }

    private static void ensureInit() {
        if(!initiated) {
            throw new IllegalStateException("Configuration is not loaded");
        }
    }

    @Override
    public String toString() {
        return "MainConfiguration{" +
                "input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", jarName='" + jarName + '\'' +
                '}';
    }
}