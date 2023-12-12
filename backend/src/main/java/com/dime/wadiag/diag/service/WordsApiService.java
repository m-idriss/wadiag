package com.dime.wadiag.diag.service;

import com.dime.wadiag.diag.model.Term;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WordsApiService {
    @GET("words/{word}/synonyms")
    Call<Term> getSynonymsForWord(@Path("word") String word, @Header("x-rapidapi-key") String apiKey);

    @GET("words/health")
    Call<ResponseBody> testWordsApiConnection(@Header("x-rapidapi-key") String apiKey);
}
