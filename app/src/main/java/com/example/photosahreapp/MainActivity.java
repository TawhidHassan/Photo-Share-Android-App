package com.example.photosahreapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView btnAddPhoto,titletext,subtitle;
    Button photobtn,chatbutn;
    View dotmenu;
    ImageView mainimage;
    Animation btt,btttwo,imgbounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //animation
        btt= AnimationUtils.loadAnimation(this,R.anim.btt);
        btttwo= AnimationUtils.loadAnimation(this,R.anim.btttwo);
        imgbounce= AnimationUtils.loadAnimation(this,R.anim.imgbounce);

        titletext=findViewById(R.id.textView);
        subtitle=findViewById(R.id.textView2);

        photobtn=findViewById(R.id.photobtn);
        chatbutn=findViewById(R.id.chatbtn);

        mainimage=findViewById(R.id.imageView);

        dotmenu=findViewById(R.id.dotmenu);



        btnAddPhoto=findViewById(R.id.textView3);
        btnAddPhoto.setPaintFlags(btnAddPhoto.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PhotoCatActivity.class);
                startActivity(intent);
            }
        });
        chatbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainimage.setImageResource(R.drawable.icchatlop);
                titletext.setText("No One Chat");
                subtitle.setText("Build a impact socity");
                btnAddPhoto.setText("Find a Friend");
                btnAddPhoto.setTextColor(Color.parseColor("#DD5145"));

//                animation
                mainimage.startAnimation(imgbounce);
                titletext.startAnimation(btt);
                subtitle.startAnimation(btt);
                btnAddPhoto.startAnimation(btttwo);
//                another animation
                photobtn.setBackgroundResource(R.drawable.iccamunlow);
                chatbutn.setBackgroundResource(R.drawable.icmsganlop);

                photobtn.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).start();
                chatbutn.animate().scaleY(1).scaleX(1).setDuration(350).start();
                dotmenu.animate().translationX(750).setStartDelay(100).setDuration(350).start();

            }
        });
        photobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainimage.setImageResource(R.drawable.icphotoslow);
                titletext.setText("No Photo");
                subtitle.setText("Add a pic for your cicle");
                btnAddPhoto.setText("Add a Photo");
                btnAddPhoto.setTextColor(Color.parseColor("#3A3D4D"));

//                animation
                mainimage.startAnimation(imgbounce);
                titletext.startAnimation(btt);
                subtitle.startAnimation(btt);
                btnAddPhoto.startAnimation(btttwo);
//                another animation
                photobtn.setBackgroundResource(R.drawable.iccamanlop);
                chatbutn.setBackgroundResource(R.drawable.icmsgunlow);

                photobtn.animate().scaleY(1).scaleX(1).setDuration(350).start();
                chatbutn.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).start();
                dotmenu.animate().translationX(0).setStartDelay(100).setDuration(350).start();

            }
        });

    }


}
