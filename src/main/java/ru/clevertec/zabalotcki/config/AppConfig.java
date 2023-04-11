package ru.clevertec.zabalotcki.config;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Data
public class AppConfig {

    private static final String CONFIG_FILE_NAME = "application.yml";
    private static AppConfig instance = null;
    private Map<String, Object> configMap;

    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    private AppConfig() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        configMap = yaml.load(inputStream);
        this.jdbcUrl = (String) configMap.get("url");
        this.jdbcUser = (String) configMap.get("user");
        this.jdbcPassword = (String) configMap.get("password");
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}
