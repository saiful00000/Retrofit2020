package com.example.retrofit2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.retrofit2020.API.Api;
import com.example.retrofit2020.adapters.CommentAdapter;
import com.example.retrofit2020.adapters.PostAdapter;
import com.example.retrofit2020.models.Comment;
import com.example.retrofit2020.models.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private PostAdapter postAdapter;
    private CommentAdapter commentAdapter;

    private Api api;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // instanciate restrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);


        getPosts();
        //getComments();

        //sendPost();

        updatePost();
    }


    private void getComments() {

        Call<List<Comment>> comments = api.getComments(5);

        comments.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    List<Comment> commentList = response.body();
                    commentAdapter = new CommentAdapter(commentList);
                    recyclerView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }


    private void getPosts() {

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("userId", "4");
        queryMap.put("_sort", "id");
        queryMap.put("_order", "desc");

        //Call<List<Post>> posts = api.getPosts();
        //Call<List<Post>> posts = api.getPostByQuery(4, "id", "desc");
        Call<List<Post>> posts = api.getPostByQueryMap(queryMap);

        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> postList = response.body();
                    postAdapter = new PostAdapter(postList);
                    recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private void sendPost() {
        //Post post = new Post(100, "Title", "Post body");

        //Call<Post> makePost = api.sendPostByUrlEncoded(1, "01 title", "01 body");

        Map <String, String> urlMap = new HashMap<>();
        urlMap.put("userId", "2");
        urlMap.put("title", "02 title");
        urlMap.put("body", "02 body");
        Call<Post> makePost = api.sendPostByUrlMap(urlMap);

        makePost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Post failed");
                    return;
                }

                Post responsePost = response.body();
                Log.i(TAG, Integer.toString(responsePost.getId()));
                Log.i(TAG, Integer.toString(responsePost.getUserId()));
                Log.i(TAG, responsePost.getTitle());
                Log.i(TAG, responsePost.getBody());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void updatePost() {

        Post post = new Post(5, "title for 5", "body for 5" );

        //Call<Post> updatePost = api.putPost(5, post);
        Call<Post> patchPost = api.patchPost(5, post);

        patchPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Log.i(TAG, "Faile while updating post");
                    return;
                }

                Post responsePost = response.body();
                Log.i(TAG, Integer.toString(responsePost.getId()));
                Log.i(TAG, Integer.toString(responsePost.getUserId()));
                Log.i(TAG, responsePost.getTitle());
                Log.i(TAG, responsePost.getBody());

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

    }

}



























