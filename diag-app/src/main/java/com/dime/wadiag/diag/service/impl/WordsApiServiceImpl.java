package com.dime.wadiag.diag.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dime.wadiag.configuration.RetrofitConfig;
import com.dime.wadiag.diag.exception.GenericError;
import com.dime.wadiag.diag.model.Term;
import com.dime.wadiag.diag.service.WordsApiService;
import com.dime.wadiag.diag.wordsapi.WordsApiProperties;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Slf4j
@Service
public class WordsApiServiceImpl {

    private WordsApiService service;

    public WordsApiServiceImpl(WordsApiProperties apiProperties) {
        service = new RetrofitConfig(apiProperties).createService(WordsApiService.class);
    }

    public Term getSynonymsForWord(String word) throws IOException {
        Call<Term> call = service.getSynonymsForWord(word);
        retrofit2.Response<Term> response = call.execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return response.body();
        } else {
            log.error(response.toString());
            throw GenericError.WORD_NOT_FOUND.exWithArguments(Map.of("word", word));
        }
    }

    public boolean testWordsApiConnection() throws IOException {
        Call<ResponseBody> call = service.testWordsApiConnection();
        retrofit2.Response<ResponseBody> response = call.execute();
        if (!response.isSuccessful()) {
            throw GenericError.FAILED_DEPENDENCY.exWithArguments(Map.of("code", response.code()));
        }
        return true;
    }
}