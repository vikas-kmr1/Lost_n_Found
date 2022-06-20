package com.lost_n_found.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lost_n_found.R;
import com.lost_n_found.databinding.FragmentPostItemBinding;
import com.lost_n_found.home.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPostItemRecyclerViewAdapter extends RecyclerView.Adapter<MyPostItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private Context context;

    public MyPostItemRecyclerViewAdapter(List<PlaceholderItem> items, Context context) {
        mValues = items;
        this.context  =context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.mItem = mValues.get(position);
        holder.mstatusTitleView.setText(mValues.get(position).status.toUpperCase());
        holder.mContentTitleView.setText(mValues.get(position).title);
        holder.mdateView.setText(mValues.get(position).date);
        Glide.with(context).load(mValues.get(position).imageUrl).into(holder.mImageView);
        holder.itemView.findViewById(R.id.linearLayoutRecycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),PostDetails.class);
                intent.putExtra("title",holder.mContentTitleView.getText());
                intent.putExtra("status",holder.mstatusTitleView.getText());
                intent.putExtra("date",holder.mdateView.getText());
                intent.putExtra("des",mValues.get(position).description);
                intent.putExtra("location",mValues.get(position).location);
                intent.putExtra("contact",mValues.get(position).contact);
                intent.putExtra("name",mValues.get(position).username);
                intent.putExtra("imgUrl",mValues.get(position).imageUrl);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mstatusTitleView;
        public final TextView mContentTitleView;
        public final ImageView mContentImageView;
        public final TextView mdateView;
        public final ImageView mImageView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentPostItemBinding binding) {
            super(binding.getRoot());
            mstatusTitleView = binding.StatusTitle;
            mContentTitleView = binding.contentTitle;
            mContentImageView = binding.image;
            mdateView = binding.dateText;
            mImageView = binding.image;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentTitleView.getText() + "'";
        }
    }
}