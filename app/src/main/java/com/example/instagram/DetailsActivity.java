package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    Post post;
    ParseUser currentUser;
    public TextView tvDescription;
    public TextView tvUsername;
    public TextView tvTimestamp;
    public ImageView ivPicture;
    public ImageButton ibLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        currentUser = ParseUser.getCurrentUser();

        // connecting views
        tvDescription = findViewById(R.id.tvDescription);
        tvUsername = findViewById(R.id.tvUsername);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivPicture = findViewById(R.id.ivPicture);
        ibLike = findViewById(R.id.ibLike);

        // setting view values
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        tvTimestamp.setText(formatter.format(post.getCreatedAt()));


        // use correct res for like status
        if (isLiked()) {
            ibLike.setImageResource(R.drawable.ufi_heart_active);
            ibLike.setTag(R.drawable.ufi_heart_active);
        } else {
            ibLike.setImageResource(R.drawable.ufi_heart);
            ibLike.setTag(R.drawable.ufi_heart);
        }

        // listeners
        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( (int) ibLike.getTag() == R.drawable.ufi_heart) {
                    likePost();
                } else {
                    unlikePost();
                }
            }
        });

        Glide.with(this)
                .load(post.getImage().getUrl())
                .into(ivPicture);
    }

    private void likePost() {
        JSONArray likeArray = post.getJSONArray("likes");

        // null check
        if (likeArray == null) {
            likeArray = new JSONArray();
        }

        likeArray.put(currentUser);
        post.setLikes(likeArray);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ibLike.setImageResource(R.drawable.ufi_heart_active);
                    ibLike.setTag(R.drawable.ufi_heart_active);
                } else {
                    Log.d("DetailsActivity", "Error liking post!");
                    e.printStackTrace();
                }
            }
        });
    }

    private void unlikePost() {
        JSONArray likeArray = post.getJSONArray("likes");

        // null check
        if (likeArray == null) {
            likeArray = new JSONArray();
        }

        for (int i = 0; i < likeArray.length(); ++i) {
            try {
                if (likeArray.getJSONObject(i).getString("objectId")
                        .equals(currentUser.getObjectId())) {
                    likeArray.remove(i);
                }
            } catch (JSONException e) {
                Log.d("DetailsActivity", "Failed to remove User from likes");
                e.printStackTrace();
            }
        }
        post.setLikes(likeArray);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    ibLike.setImageResource(R.drawable.ufi_heart);
                    ibLike.setTag(R.drawable.ufi_heart);
                } else {
                    Log.d("DetailsActivity", "Error unliking post!");
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isLiked() {
        JSONArray likeArray = post.getJSONArray("likes");

        // null check
        if (likeArray == null) {
            likeArray = new JSONArray();
        }

        for (int i = 0; i < likeArray.length(); ++i) {
            try {
                if (likeArray.getJSONObject(i).getString("objectId")
                        .equals(currentUser.getObjectId())) {
                    return true;
                }
            } catch (JSONException e) {
                    Log.d("DetailsActivity", "Failed to get User like status");
                    e.printStackTrace();
            }
        }
        return false;
    }
}
