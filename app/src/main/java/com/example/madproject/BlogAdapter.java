package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.InputStream;

public class BlogAdapter extends FirestoreRecyclerAdapter<Blog, BlogAdapter.BlogViewHolder> {

    Context context;

    public BlogAdapter(@NonNull FirestoreRecyclerOptions<Blog> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull Blog blog) {
        holder.contentTxtView.setText(blog.content);
        new DownloadImageTask(holder.imageTextView)
                .execute(blog.image);

        String docId = this.getSnapshots().getSnapshot(position).getId();
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.popup);
                popupMenu.getMenu().add("Edit");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle() == "Edit"){
                                Intent intent = new Intent(context, EditBlogActivity.class);
                                intent.putExtra("title", blog.title);
                                intent.putExtra("content",blog.content);
                                intent.putExtra("image",blog.image);
                                intent.putExtra("tags",blog.tags);
                                intent.putExtra("docId", docId);
                                context.startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_blog_item,parent,false);
        return  new BlogViewHolder(view);
    }

    class BlogViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView, contentTxtView, tagTextView;
        ImageView imageTextView, popup;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            imageTextView = itemView.findViewById(R.id.blogImage);
            contentTxtView = itemView.findViewById(R.id.blogContent);
            popup = itemView.findViewById(R.id.popupMenu);
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
