package com.example.photosahreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotoCatActivity extends AppCompatActivity {

    RecyclerView myCategories;
    CategoriesAdapter categoriesAdapter;
    List<Categories> categoriesList;
    Button button;
    Animation btt,btttwo,imgbounce;

    String USER_NAME_STORY="usrnamestory";
    String UserNameStoryLocal="";
    String UserNameStoryNew="";

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_cat);

        loaddataUserNameLocal();



        button=findViewById(R.id.butnid);
        btt= AnimationUtils.loadAnimation(this,R.anim.btt);
        btttwo= AnimationUtils.loadAnimation(this,R.anim.btttwo);
        imgbounce= AnimationUtils.loadAnimation(this,R.anim.imgbounce);

        categoriesList=new ArrayList<>();

        categoriesList.add(
                new Categories(
                        "Juicy",
                        R.drawable.icjuicy
                )
        );
        categoriesList.add(
                new Categories(
                        "Snorkling",
                        R.drawable.icsnork
                )
        );
        categoriesList.add(
                new Categories(
                        "Shopping",
                        R.drawable.icshopper
                )
        );
        categoriesList.add(
                new Categories(
                        "Study",
                        R.drawable.icstudy
                )
        );
        categoriesList.add(
                new Categories(
                        "Travel",
                        R.drawable.ictravel
                )
        );
        categoriesList.add(
                new Categories(
                        "DroopPrize",
                        R.drawable.icprize
                )
        );

        myCategories=findViewById(R.id.catelistId);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        myCategories.setLayoutManager(linearLayoutManager);
        myCategories.setHasFixedSize(true);

        categoriesAdapter=new CategoriesAdapter(this,categoriesList);
        myCategories.setAdapter(categoriesAdapter);


        //snapping from google for scroling item well
        final SnapHelper snapHelper=new LinearSnapHelper();
        snapHelper.attachToRecyclerView(myCategories);


        //that was for first item unanimation
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder=myCategories.findViewHolderForAdapterPosition(0);
                ImageView imageView=viewHolder.itemView.findViewById(R.id.itemImageId);
                imageView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100).start();

                TextView textView=viewHolder.itemView.findViewById(R.id.imgeTitleId);
                textView.animate().alpha(1).setDuration(100).start();
                button.setAlpha(1);
                button.setAnimation(btt);
            }
        },100);


        reference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(UserNameStoryNew);
        //scroll lisitener
        myCategories.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE)
                {
                    View view=snapHelper.findSnapView(linearLayoutManager);
                    int pos=linearLayoutManager.getPosition(view);
                    RecyclerView.ViewHolder viewHolder=myCategories.findViewHolderForAdapterPosition(pos);
                    ImageView imageView=viewHolder.itemView.findViewById(R.id.itemImageId);
                    imageView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(100).start();

                    final  TextView categoryimageTitle=viewHolder.itemView.findViewById(R.id.imgeTitleId);
                    categoryimageTitle.animate().alpha(1).setDuration(100).start();

                    button.setAlpha(1);
                    button.setAnimation(btt);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //save category to database
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // get string from current category
                                    String storycate = categoryimageTitle.getText().toString();
                                    //save data in database
                                    reference.getRef().child("category").setValue(storycate);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //save end

                            Intent intent=new Intent(getApplicationContext(),ChoseUserNameActivity.class);
                            startActivity(intent);
                        }
                    });

                }

                else {
                    View view=snapHelper.findSnapView(linearLayoutManager);
                    int pos=linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder=myCategories.findViewHolderForAdapterPosition(pos);
                    ImageView imageView=viewHolder.itemView.findViewById(R.id.itemImageId);
                    imageView.animate().alpha(0.5f).scaleX(0.5f).scaleY(0.5f).setDuration(100).start();

                    TextView textView=viewHolder.itemView.findViewById(R.id.imgeTitleId);
                    textView.animate().alpha(0).setDuration(100).start();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                button.setAlpha(0);
            }
        });



    }
    public void loaddataUserNameLocal()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(USER_NAME_STORY,MODE_PRIVATE);
        UserNameStoryNew=sharedPreferences.getString(UserNameStoryLocal,"");

    }
}
