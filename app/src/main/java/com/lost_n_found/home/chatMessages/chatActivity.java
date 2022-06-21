package com.lost_n_found.home.chatMessages;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lost_n_found.R;

import java.util.ArrayList;

public class chatActivity extends AppCompatActivity {

    private RecyclerView messageRecyclerView;
    private EditText messageBox;
    private ShapeableImageView sentBtn,mAvatar;
    private TextView nameTxt;
    private ImageView bacckbtn;
    private DatabaseReference mDbRef;
    ArrayList<CreateMessage> messageArrayList ;
    MessageAdapter messageAdapter;

    String receiverRoom = null;
    String senderRoom = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        messageRecyclerView = (RecyclerView) findViewById(R.id.messageRecycler);
        messageBox = (EditText) findViewById(R.id.messageText);

        sentBtn = (ShapeableImageView) findViewById(R.id.sendBtn);
        mAvatar = (ShapeableImageView) findViewById(R.id.toolbatAvatar);

        nameTxt = (TextView) findViewById(R.id.toolbarName);
        bacckbtn = (ImageView) findViewById(R.id.backButtonChat);

        ArrayList<CreateMessage> messageArrayList = new ArrayList<CreateMessage>();

        messageAdapter  = new MessageAdapter(this,messageArrayList) ;


        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(messageAdapter);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String avatar = intent.getStringExtra("avatar");
        String recieverUid = intent.getStringExtra("uid");

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        senderRoom = recieverUid + senderUid;
        receiverRoom = senderUid + recieverUid;

        if(messageBox.getText().toString().equals("")){
            sentBtn.setEnabled(false);
            sentBtn.setAlpha(0.5f);
        }

        nameTxt.setText(name);
        Glide.with(getApplicationContext()).load(avatar).into(mAvatar);

        bacckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        messageBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 0){
                    sentBtn.setEnabled(false);
                    sentBtn.setAlpha(0.5f);
                }
                else{
                    sentBtn.setEnabled(true);
                    sentBtn.setAlpha(1f);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mDbRef = firebaseDatabase.getReference();

        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentMessage = messageBox.getText().toString();
                CreateMessage createMessage = new CreateMessage(currentMessage+"",senderUid+"");

                mDbRef.child("chats").child(senderRoom).child("messages").push().setValue(createMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mDbRef.child("chats").child(receiverRoom).child("messages").push().setValue(createMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                messageBox.setText("");
                            }
                        });
                    }
                }) ;


            }



        });


        //logic for adding datat to recycler view
        mDbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageArrayList.clear();
                        for(DataSnapshot snap : snapshot.getChildren()){
                            CreateMessage createMessage = snap.getValue(CreateMessage.class);
                            assert createMessage != null;
                            messageArrayList.add(new CreateMessage(createMessage.getMessage()+"", createMessage.getSenderId()+""));
                        }
                        messageAdapter.notifyDataSetChanged();
                        messageRecyclerView.scrollToPosition(messageAdapter.getItemCount()-1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}