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
import com.lost_n_found.databinding.FragmentLostBinding;
import com.lost_n_found.home.placeholder.PlaceholderLostContent.PlaceholderItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLostItemRecyclerViewAdapter extends RecyclerView.Adapter<MyLostItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private final Context context;

    public MyLostItemRecyclerViewAdapter(List<PlaceholderItem> items , Context context) {
        mValues = items;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.mItem = mValues.get(position);
        // holder.mIdView.setText(mValues.get(position).uid);
        holder.mContentTitle.setText(mValues.get(position).title);
        holder.mContentlostBy.setText(mValues.get(position).lostBy);
        holder.mContentDate.setText(mValues.get(position).date);
        Glide.with(context).load(mValues.get(position).imageUrl).into(holder.mContentimage);

        holder.itemView.findViewById(R.id.linearLayoutRecycler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(),PostDetails.class);
                intent.putExtra("title",holder.mContentTitle.getText());
                intent.putExtra("status",holder.mContentTitle.getText());
                intent.putExtra("date",holder.mContentDate.getText());
                intent.putExtra("des",mValues.get(position).description);
                intent.putExtra("location",mValues.get(position).location);
                intent.putExtra("contact",mValues.get(position).contact);
                intent.putExtra("name","Lost By: "+mValues.get(position).lostBy);
                intent.putExtra("imgUrl",mValues.get(position).imageUrl);
                intent.putExtra("postuid",mValues.get(position).uid);
                intent.putExtra("avatar",mValues.get(position).avatar);
                intent.putExtra("btnTitle","Help");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       // public final TextView mIdView;
        public final TextView mContentTitle;
        public final TextView mContentlostBy;
        public final TextView mContentDate;
        public PlaceholderItem mItem;
        public ImageView mContentimage;

        public ViewHolder(FragmentLostBinding binding) {
            super(binding.getRoot());
           // mIdView = binding.itemNumber;
            mContentTitle = binding.contentTitle;
            mContentlostBy = binding.LostByText;
            mContentDate= binding.dateText;
            mContentimage =binding.imageLost;
        }


    }
}