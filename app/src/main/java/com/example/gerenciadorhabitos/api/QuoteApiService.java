package com.example.gerenciadorhabitos.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteApiService {
    @GET("api/random")
    Call<List<Quote>> getRandomQuote();
}
