package com.lost_n_found.home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lost_n_found.R;
import com.lost_n_found.home.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class PostItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public  Context context;
    public boolean isDeleted = false;
    public  RecyclerView recyclerView;
    public static MyPostItemRecyclerViewAdapter myPostItemRecyclerViewAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback;
    SwipeToDeleteCallback swipeToDeleteCallback;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostItemFragment newInstance(int columnCount) {
        PostItemFragment fragment = new PostItemFragment();
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

        {
            swipeToDeleteCallback = new SwipeToDeleteCallback(context,getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_twotone_delete_forever_24)) {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                    final int position = viewHolder.getAdapterPosition();
                    PlaceholderContent.PlaceholderItem deleted = PlaceholderContent.ITEMS.get(position);
                    PlaceholderContent.ITEMS.remove(position);
                    isDeleted = true;
                    myPostItemRecyclerViewAdapter.notifyDataSetChanged();


                    Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Item was removed from the list.",3000);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            PlaceholderContent.ITEMS.add(position,deleted);
                            isDeleted = false;
                            myPostItemRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });

                    snackbar.show();

                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           if(isDeleted){
                               try{
                                   FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                   FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                   String uid = firebaseUser.getUid();
                                   FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                   DatabaseReference databaseReferencePosts = firebaseDatabase.getReference("posts/" + uid);
                                   databaseReferencePosts.addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                                           for (DataSnapshot snapshot1 : snapshot.getChildren()){

                                               CreatePost post = snapshot1.getValue(CreatePost.class);
                                               assert post != null;
                                               if( (post.date.toString().trim().equals(deleted.date.toString().trim())) &&
                                                       (post.description.toString().trim().equals(deleted.description.toString().trim())) &&
                                                       (post.title.toString().trim().equals(deleted.title.toString().trim())) &&
                                                       (post.status.toString().trim().equals(deleted.status.toString().trim())) &&
                                                       (post.location.toString().trim().equals(deleted.location.toString().trim())))
                                               {
                                                   databaseReferencePosts.child(snapshot1.getKey().toString()).removeValue();

                                                   LostFragment.myLostItemRecyclerViewAdapter.notifyDataSetChanged();
                                                   FoundFragment.myFoundItemRecyclerViewAdapter.notifyDataSetChanged();
                                               }




                                           }
                                       }

                                       @Override
                                       public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                                       }
                                   });}
                               catch (Exception e){}

                           }

                       }
                   },3010);




                }
            };


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_item_list, container, false);


        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myPostItemRecyclerViewAdapter = (new MyPostItemRecyclerViewAdapter(PlaceholderContent.ITEMS, getContext()));
            recyclerView.setAdapter(myPostItemRecyclerViewAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);



        }
        return view;
    }




}