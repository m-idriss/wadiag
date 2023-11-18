package com.dime.wadiag.diag.wordsapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "wordsapi.host")
@Component
public class WordsApiProperties {

    private String key;

    private String url;

    @Getter
    @AllArgsConstructor
    public enum Category {

        EVERYTHING(""),
        SYNONYMS("synonyms"),
        EXAMPLES("examples");

        private final String name;
    }
}
