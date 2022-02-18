package com.lsmayl1.loginregister;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
public class MainActivity extends AppCompatActivity {
    private Button logout, fragmentBtn1,fragmentBtn2,profilePic;
    private Firstfragment firstfragment = new Firstfragment();
    private Uri imageUri;
    private FirebaseStorage storage;
    private com.google.firebase.storage.StorageReference StorageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);     // upload
        storage = FirebaseStorage.getInstance();
        StorageReference = storage.getReference();
        profilePic = findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }
    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            uploadPicture();
        } }
    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("uploading picture processing");
        pd.show();
        final  String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = StorageReference.child("images/" + randomKey);
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"image Uploaded", Snackbar.LENGTH_LONG).show();
                    }})
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Faileed TO Upload", Toast.LENGTH_LONG).show();
                    }});
        // fragment
        fragmentBtn1 = findViewById(R.id.fragmentBtn1);
        fragmentBtn2 = findViewById(R.id.fragmentBtn2);
        setNewFragment(firstfragment);
        fragmentBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNewFragment(firstfragment);
            }});
        fragmentBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment secondFragment = new SecondFragment();
                setNewFragment(secondFragment);
            }
        });




        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                finish();

            }
        });


    }

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();


    }
}

   /* uploadBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        startActivity(new Intent(MainActivity.this,uploadphoto.class ));
        }
        });*/