package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private String title, description, content, url,imageurl;

    private TextView titletv, tvDesc, tvContent;
    private ImageView imagenews;
    private Button readFullnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        content = getIntent().getStringExtra("content");
        imageurl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        titletv = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDescription);
        tvContent = findViewById(R.id.tvContent);
        imagenews = findViewById(R.id.imagenews);
        readFullnews = findViewById(R.id.btnfullNews);

        titletv.setText(title);
        tvDesc.setText(description);
        tvContent.setText(content);
        Picasso.get().load(imageurl).into(imagenews);

        readFullnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url != null && !url.isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        });
    }
}
