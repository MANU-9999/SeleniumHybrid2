package core.testutils;


import com.google.gson.Gson;
import core.constants.Env;
import core.env_config.Config;
import core.env_config.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtility {
    // Read the configuration for the selected environment (DEV, QA, UAT)
    public static Environment readEnvironmentConfig(Env env) {
        Gson gson = new Gson();
        File jsonFile = new File(System.getProperty("user.dir") + "//ConfigurationS//config.json");
        FileReader fileReader = null;
        Environment environment;
        try {
            fileReader = new FileReader(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Config config = gson.fromJson(fileReader, Config.class);
        environment = config.getEnvironments().get("QA");
        return environment;
    }
}
