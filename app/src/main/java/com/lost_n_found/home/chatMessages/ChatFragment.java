package com.lost_n_found.home.chatMessages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lost_n_found.R;
import com.lost_n_found.databinding.FragmentChatBinding;
import com.lost_n_found.home.chatMessages.placeholder.PlaceholderChatContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ChatFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChatFragment newInstance(int columnCount) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(PlaceholderChatContent.ITEMS, context));
        }
        return view;
    }

    /**
     * {@link RecyclerView.Adapter} that can display a {@link PlaceholderChatContent.PlaceholderItem}.
     * TODO: Replace the implementation with code for your data type.
     */
    public static class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

        private final List<PlaceholderChatContent.PlaceholderItem> mValues;
        private final Context context;

        public MyItemRecyclerViewAdapter(List<PlaceholderChatContent.PlaceholderItem> items, Context context) {
            mValues = items;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(FragmentChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.mItem = mValues.get(position);
            holder.mName.setText(mValues.get(position).name);
            Glide.with(context).load(mValues.get(position).avatar).into(holder.mAvatar);


            holder.itemView.findViewById(R.id.linearLayoutRecycler).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , chatActivity.class);
                    intent.putExtra("name",mValues.get(position).name);
                    intent.putExtra("avatar",mValues.get(position).avatar);
                    intent.putExtra("uid",mValues.get(position).uid);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView mName;
            public final ImageView mAvatar;
            public PlaceholderChatContent.PlaceholderItem mItem;

            public ViewHolder(FragmentChatBinding binding) {
                super(binding.getRoot());
                mName = binding.name;
                mAvatar = binding.avatarImg;
            }


        }
    }
}