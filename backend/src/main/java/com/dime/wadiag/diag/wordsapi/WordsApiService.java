package com.dime.wadiag.diag.wordsapi;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dime.wadiag.diag.word.Word;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
@Service
public class WordsApiService {
    private WordsApiInterface wordsApiInterface;

    private WordsApiProperties wordsApiProperties;

    public static final String API_URL = "https://wordsapiv1.p.rapidapi.com";

    public WordsApiService(WordsApiProperties apiProperties) {
        this.wordsApiProperties = apiProperties;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wordsApiInterface = retrofit.create(WordsApiInterface.class);
    }

    public WordsApiResponse getSynonymsForWord(String word) throws ResourceNotFoundException, IOException {
        try {
            Call<WordsApiResponse> call = wordsApiInterface.getSynonymsForWord(word,
                    wordsApiProperties.getKey());
            retrofit2.Response<WordsApiResponse> response = call.execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                Word entity = Word.builder()
                        .name(word)
                        .build();
                log.info(entity.toString());
                return response.body();
            } else {
                log.error(response.toString());
                throw new ResourceNotFoundException(WordsApiProperties.Category.SYNONYMS.getName(), word);

            }
        } catch (IOException e) {
            // Handle exceptions
            throw e;
        }
    }
}