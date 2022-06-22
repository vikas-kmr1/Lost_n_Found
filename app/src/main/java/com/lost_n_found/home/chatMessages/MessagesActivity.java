package com.lost_n_found.home.chatMessages;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.lost_n_found.R;
import com.lost_n_found.home.chatMessages.placeholder.PlaceholderChatContent;

public class MessagesActivity extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        toolbar =  findViewById(R.id.toolbarMessages);
        ImageView noMessage = (ImageView) findViewById(R.id.nomessageChat);
        TextView noMessageTxt = (TextView) findViewById(R.id.nochat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentTransaction Profile = getSupportFragmentManager().beginTransaction();
        Profile.replace(R.id.messageFrame, new ChatFragment());
        Profile.commitAllowingStateLoss();

        if(PlaceholderChatContent.ITEMS.size() == 0){
            noMessage.setVisibility(View.VISIBLE);
            noMessageTxt.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}