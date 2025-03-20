package core_configuration.testutils;


import com.google.gson.Gson;
import core_configuration.env_config.Environment;
import core_configuration.env_config.Config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtility {
    // Read the configuration for the selected environment (DEV, QA, UAT)
    public static Environment readEnvironmentConfig(String env) {
        Gson gson = new Gson();
        File jsonFile = new File(System.getProperty("user.dir") + "//ConfigurationS//config.json");
        Config config = null;
        try (FileReader fileReader = new FileReader(jsonFile)) {
            config = gson.fromJson(fileReader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (config != null) {
            // Return the environment data for the specified environment
            return config.getEnvironments().get(env.toUpperCase());
        }
        return null; // Return null if config is not found or JSON is malformed
    }
}
