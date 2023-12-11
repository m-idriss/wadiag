package com.dime.wadiag.diag.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dime.wadiag.diag.dto.WordDto;
import com.dime.wadiag.diag.service.WordsApiService;
import com.dime.wadiag.diag.wordsapi.WordsApiProperties;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Slf4j
@Service
public class WordsApiServiceImpl {

    @Autowired
    private WordsApiProperties wordsApiProperties;

    private WordsApiService service;

    public WordsApiServiceImpl(WordsApiProperties apiProperties) {
        this.wordsApiProperties = apiProperties;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(wordsApiProperties.getUrl())
                .addConverterFactory(GsonConverterFactory.create()).build();

        service = retrofit.create(WordsApiService.class);
    }

    public WordDto getSynonymsForWord(String word) throws IOException {
        Call<WordDto> call = service.getSynonymsForWord(word, wordsApiProperties.getKey());
        retrofit2.Response<WordDto> response = call.execute();

        if (response.isSuccessful()) {
            assert response.body() != null;
            return response.body();
        } else {
            log.error(response.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "synonyms not found for: " + word);

        }
    }

    public boolean testWordsApiConnection() throws IOException {
        Call<ResponseBody> call = service.testWordsApiConnection(wordsApiProperties.getKey());
        retrofit2.Response<ResponseBody> response = call.execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to connect to WordsAPI. HTTP status code: " + response.code());
        }
        return true;
    }
}