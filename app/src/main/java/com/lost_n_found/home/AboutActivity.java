package com.lost_n_found.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lost_n_found.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageButton backBtn = findViewById(R.id.backButtonTopabout);
        TextView email = findViewById(R.id.about_dev_info_emial);
        ImageView linkedin= findViewById(R.id.linkedin);
        ImageView github= findViewById(R.id.github);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webIntend("https://www.linkedin.com/in/vikas-kumar-9151b7191/");
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webIntend("https://github.com/vickyrules");
            }
        });


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setTextColor(Color.rgb(8,180,193));

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:vickyrules1705@gmail.com")); // only email apps should handle this
               // intent.putExtra(Intent.EXTRA_EMAIL, "vickyrules1705@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT,"<Subject here>");
                intent.putExtra(Intent.EXTRA_TEXT,"Hey Vikas,");
                startActivity(intent);
            }


        });

    }






    public void webIntend (String Url)
    {


                Uri webpage = Uri.parse(Url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(Url));
                startActivity(intent);



    }
}