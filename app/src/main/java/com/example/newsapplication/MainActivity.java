package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

    private RecyclerView newsitem,category;
    private ProgressDialog pd;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModal> categoryModalArrayList;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsitem=findViewById(R.id.recylerview_news);
        category=findViewById(R.id.recylerview_category);
        pd=new ProgressDialog(this);
        pd.setMessage("Content is Loading..Please wait");
        articlesArrayList=new ArrayList<>();
        categoryModalArrayList=new ArrayList<>();

        newsAdapter=new NewsAdapter(articlesArrayList,this);
        categoryAdapter=new CategoryAdapter(categoryModalArrayList,this,this::onCategoryClick);

        newsitem.setLayoutManager(new LinearLayoutManager(this));
        newsitem.setAdapter(newsAdapter);
        category.setAdapter(categoryAdapter);
        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();


    }

    private void getCategories(){
        categoryModalArrayList.add(new CategoryModal("All","https://images.unsplash.com/photo-1493612276216-ee3925520721?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Z2VuZXJhbCUyMG5ld3N8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Technology","https://images.unsplash.com/photo-1460925895917-afdab827c52f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8dGVjaG5vbG9neSUyMG5ld3N8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Science","https://images.unsplash.com/photo-1462331940025-496dfbfc7564?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8c2NpZW5jZSUyMG5ld3N8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Sports","https://images.unsplash.com/photo-1597649475360-955f18d9d277?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c3BvcnRzJTIwbmV3c3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("General","https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Z2VuZXJhbCUyMG5ld2F8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Business","https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YnVzaW5lc3MlMjBuZXdzfGVufDB8fDB8fHww&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Entertainment","https://images.unsplash.com/photo-1618609377864-68609b857e90?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8ZW50ZXJ0YWlubWVudCUyMG5ld3N8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));
        categoryModalArrayList.add(new CategoryModal("Health","https://images.unsplash.com/photo-1618498082410-b4aa22193b38?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fGhlYWx0aCUyMG5ld3N8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=500&q=60"));

        categoryAdapter.notifyDataSetChanged();

    }

    private void getNews(String Category)
    {
        pd.show();
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+Category+"&apiKey=a2ebc4ae7a3047f3a93938d9966f8219";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=a2ebc4ae7a3047f3a93938d9966f8219";
        String baseUrl="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApi retrofitApi=retrofit.create(RetrofitApi.class);
        Call<NewsModal> call;
        if(Category.equals("All"))
        {
            call=retrofitApi.getallNews(url);
        }
        else {
            call=retrofitApi.getallCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal=response.body();
                pd.dismiss();
                ArrayList<Articles> articles=newsModal.getArticles();
                for(int i=0;i<articles.size();i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrlToImage(),articles.get(i).getContent()));

                }
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get News", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category=categoryModalArrayList.get(position).getCategory();
        getNews(category);
    }
}