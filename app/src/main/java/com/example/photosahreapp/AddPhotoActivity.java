package com.example.photosahreapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView previewImg,logoimg;
    TextView previewTitle,title,subtitle;
    Button addbtn,continuebtn;
    Integer photomax=1;
    Uri photoLocation;

    String USER_NAME_STORY="usrnamestory";
    String UserNameStoryLocal="";
    String UserNameStoryNew="";

    DatabaseReference reference;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        loaddataUserNameLocal();

        previewImg=findViewById(R.id.photostoryId);
        logoimg=findViewById(R.id.imageView3);

        previewTitle=findViewById(R.id.phototitleId);
        title=findViewById(R.id.textView5);
        subtitle=findViewById(R.id.textView6);

        addbtn=findViewById(R.id.addbuttonId);
        continuebtn=findViewById(R.id.continuebtnId);

        previewTitle.setAlpha(0);
        continuebtn.setAlpha(0);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findphoto();
            }
        });
        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savephotoToFirbase();
            }
        });


    }

    public void savephotoToFirbase()
    {
        reference= FirebaseDatabase.getInstance().getReference().child("User").child(UserNameStoryNew);
        storageReference= FirebaseStorage.getInstance().getReference().child("User").child(UserNameStoryNew);
        if (photoLocation!=null)
        {
            StorageReference storageReference1=storageReference.child(System.currentTimeMillis()
            +","+getFileExtension(photoLocation));

            storageReference1.putFile(photoLocation).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String uriphoto=taskSnapshot.getUploadSessionUri().toString();

                    reference.getRef().child("photo").setValue(uriphoto);
                }
            });

        }
    }

    public void findphoto()
    {
        Intent pic=new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic,photomax);
    }

    String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==photomax && resultCode==RESULT_OK && data !=null && data.getData() != null)
        {
            logoimg.animate().alpha(0).setDuration(350).start();
            title.animate().alpha(0).setDuration(350).start();
            subtitle.animate().alpha(0).setDuration(350).start();
            addbtn.animate().alpha(0).setDuration(350).start();

            previewTitle.animate().alpha(1).setDuration(350).start();
            continuebtn.animate().alpha(1).setDuration(350).start();



//            texting user name local
//            previewTitle.setText(UserNameStoryNew);

            photoLocation=data.getData();
            Picasso.with(this).load(photoLocation).centerCrop().fit().into(previewImg);
        }
    }

    public void loaddataUserNameLocal()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(USER_NAME_STORY,MODE_PRIVATE);
        UserNameStoryNew=sharedPreferences.getString(UserNameStoryLocal,"");

    }


}
