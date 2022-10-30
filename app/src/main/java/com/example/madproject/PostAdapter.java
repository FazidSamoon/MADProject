package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
        if (model.createdBy.equals(Utilities.getCurrentUser().getUid().toString())) {
            holder.button.setVisibility(View.VISIBLE);
        }
//        holder.user.setText(model.createdBy);
        new DownloadImageTask(holder.image)
                .execute(model.imageUrl);

        String docId = this.getSnapshots().getSnapshot(position).getId();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.button);
                popupMenu.getMenu().add("Edit");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("Edit")) {
                            Intent intent = new Intent(context, CreatePostActivity.class);
                            intent.putExtra("title", model.title);
                            intent.putExtra("description", model.description);
                            intent.putExtra("imageUrl", model.imageUrl);
                            intent.putExtra("tags", model.tags);
                            intent.putExtra("docId", docId);
                            context.startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });


        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> post = new HashMap<>();
                DocumentReference documentReference = Utilities.getExistingCollectionReference(docId);

                if (!model.likes.isEmpty()) {
                    if (model.likes.contains(Utilities.getCurrentUser().getUid())) {
                        model.likes.remove(Utilities.getCurrentUser().getUid());

                        post.put("likes", model.likes);
                    } else {
                        model.likes.add(Utilities.getCurrentUser().getUid());
                        post.put("likes", model.likes);
                    }
                } else {
                    model.likes.add(Utilities.getCurrentUser().getUid());
                    post.put("likes", model.likes);
                }


                documentReference.update(post);
            }
        });

        System.out.println("ssss" +model.likes);
        DocumentReference documentReference = Utilities.getExistingCollectionReference(docId);
        holder.likeText.setText(model.likes.size() + " likes");

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
        ImageButton button, likeButton;

        public postViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.postImage);
            likeText = itemView.findViewById(R.id.likesText);
            user = itemView.findViewById(R.id.username);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            title = itemView.findViewById(R.id.titlePost);
            description = itemView.findViewById(R.id.descPost);
            button = itemView.findViewById(R.id.moreButton);
            likeButton = itemView.findViewById(R.id.likeButton);
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
