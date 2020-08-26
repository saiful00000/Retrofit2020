package com.example.retrofit2020.API;

import com.example.retrofit2020.models.Comment;
import com.example.retrofit2020.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts")
    Call<List<Post>> getPostByQuery(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPostByQueryMap(
            @QueryMap Map<String, String> parameter
    );

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int userId);


    @POST("posts")
    Call<Post> sendPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> sendPostByUrlEncoded(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String body
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> sendPostByUrlMap(@FieldMap Map<String, String> fields);

    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PUT("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

}
