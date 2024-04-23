package com.example.practice_5_mirea.api;

import com.example.practice_5_mirea.api.DTO.request_dto.BookToPublishDTO;
import com.example.practice_5_mirea.api.DTO.response_dto.BookDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FakeRestAPI {
    @GET("api/v1/Books")
    Call<List<BookDTO>> getBooks();

    @POST("api/v1/Books")
    Call<Void> postBook(@Body BookToPublishDTO bookToPublishDTO);

    @DELETE("api/v1/Books/{id}")
    Call<Void> deleteBookWithId(@Path("id") int id);
}
