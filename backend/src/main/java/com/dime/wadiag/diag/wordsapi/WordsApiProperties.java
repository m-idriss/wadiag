package com.dime.wadiag.diag.wordsapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

@Getter
@Setter
@Component
public class WordsApiProperties {

    private String key;
    private String url;

    public WordsApiProperties(Dotenv dotenv) {
        this.key = dotenv.get("WORDSAPI_KEY");
        this.url = dotenv.get("WORDSAPI_URL");
    }

    @Getter
    @AllArgsConstructor
    public enum Category {
        EVERYTHING(""),
        SYNONYMS("synonyms"),
        EXAMPLES("examples");

        private final String name;
    }
}
