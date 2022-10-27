package com.example.madproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PostAdapter extends FirestoreRecyclerAdapter<Post, PostAdapter.postViewHolder> {
    Context context;


    public PostAdapter(@NonNull FirestoreRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull postViewHolder holder, int position, @NonNull Post model) {
        holder.title.setText(model.title);
        holder.description.setText(model.description);
        holder.user.setText(model.createdBy);
        new DownloadImageTask(holder.image)
                .execute(model.imageUrl);
    }


    @NonNull
    @Override
    public postViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_post, parent, false);
        return new postViewHolder(view);
    }


    class postViewHolder extends RecyclerView.ViewHolder {
        TextView likeText, user, timeStamp, title, description;
        ImageView image;

        public postViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.postImage);
            likeText = itemView.findViewById(R.id.likesText);
            user = itemView.findViewById(R.id.username);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            title = itemView.findViewById(R.id.titlePost);
            description = itemView.findViewById(R.id.descPost);
        }

    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
