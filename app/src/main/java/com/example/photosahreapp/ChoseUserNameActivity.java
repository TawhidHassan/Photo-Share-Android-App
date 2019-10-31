package com.example.photosahreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChoseUserNameActivity extends AppCompatActivity {

    Button button;
    EditText editText;

    String USER_NAME_STORY="usrnamestory";
    String UserNameStoryLocal="";
    String UserNameStoryNew="";

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_user_name);

        loaddataUserNameLocal();
        checkUserNameLocal();

        button=findViewById(R.id.butnid);
        editText=findViewById(R.id.usernameId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference= FirebaseDatabase.getInstance().getReference()
                            .child("User").child(editText.getText().toString());

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //save data in database
                        reference.getRef().child("Username").setValue(editText.getText().toString());

                        //save data local storage as a kye when ever get the data from firebase
                        SharedPreferences sharedPreferences=getSharedPreferences(USER_NAME_STORY,MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(UserNameStoryLocal,editText.getText().toString());
                        editor.apply();

                        //give notification
                        Toast.makeText(getApplicationContext(),"Ok Done",Toast.LENGTH_LONG).show();
                        editText.setText("");
                        //anoter activity
                        Intent intent=new Intent(getApplicationContext(),AddPhotoActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    public void loaddataUserNameLocal()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(USER_NAME_STORY,MODE_PRIVATE);
        UserNameStoryNew=sharedPreferences.getString(UserNameStoryLocal,"");

    }
    public  void checkUserNameLocal()
    {
        if (UserNameStoryNew.isEmpty())
        {

        }else {
            Intent intent=new Intent(getApplicationContext(),AddPhotoActivity.class);
            startActivity(intent);
        }
    }
}
