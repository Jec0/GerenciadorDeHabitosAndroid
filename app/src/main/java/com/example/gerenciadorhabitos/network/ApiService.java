package com.example.gerenciadorhabitos.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;
import com.example.gerenciadorhabitos.model.Habit;

public interface ApiService {
    @GET("/habits")
    Call<List<Habit>> fetchHabits();

    @POST("/habits")
    Call<Habit> pushHabit(@Body Habit habit);
}
