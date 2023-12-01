package com.dime.wadiag.diag.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.service.WordsApiService;
import com.dime.wadiag.diag.wordsapi.ResourceNotFoundException;
import com.dime.wadiag.diag.wordsapi.WordsApiProperties;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
@Service
public class WordsApiServiceImpl {

    private WordsApiService wordsApiInterface;

    @Autowired
    private WordsApiProperties wordsApiProperties;

    public WordsApiServiceImpl(WordsApiProperties apiProperties) {
        this.wordsApiProperties = apiProperties;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(wordsApiProperties.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        wordsApiInterface = retrofit.create(WordsApiService.class);
    }

    public WordDto getSynonymsForWord(String word) throws ResourceNotFoundException, IOException {
        try {
            Call<WordDto> call = wordsApiInterface.getSynonymsForWord(word,
                    wordsApiProperties.getKey());
            retrofit2.Response<WordDto> response = call.execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body();
            } else {
                log.error(response.toString());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "synonyms not found for: " + word);

            }
        } catch (IOException e) {
            // Handle exceptions
            log.info("Failed to getSynonymsForWord, cause : " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "synonyms not found for: " + word, e);
        }
    }

    public boolean testWordsApiConnection() throws IOException {
        try {
            // Perform a test request to WordsAPI to check the connection
            Call<ResponseBody> call = wordsApiInterface.testWordsApiConnection(wordsApiProperties.getKey());
            retrofit2.Response<ResponseBody> response = call.execute();

            if (!response.isSuccessful()) {
                throw new IOException("Failed to connect to WordsAPI. HTTP status code: " + response.code());
            }

            return true;
        } catch (IOException e) {
            log.error("Failed to test WordsAPI connection: " + e.getMessage());
            throw e;
        }
    }
}