package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    public TextView tvDescription;
    public TextView tvUsername;
    public TextView tvTimestamp;
    public ImageView ivPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvDescription = findViewById(R.id.tvDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivPicture = findViewById(R.id.ivPicture);

        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        tvTimestamp.setText(formatter.format(post.getCreatedAt()));

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivPicture);
    }
}
