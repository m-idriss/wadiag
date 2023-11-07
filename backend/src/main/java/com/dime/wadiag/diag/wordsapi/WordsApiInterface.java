package com.dime.wadiag.diag.wordsapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WordsApiInterface {
    @GET("words/{word}/synonyms")
    Call<WordsApiResponse> getSynonymsForWord(@Path("word") String word, @Header("x-rapidapi-key") String apiKey);
}
