package com.lsmayl1.loginregister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private Button logout, fragmentBtn1,fragmentBtn2,uploadBtn;
   private Firstfragment firstfragment = new Firstfragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentBtn1 = findViewById(R.id.fragmentBtn1);
        fragmentBtn2 = findViewById(R.id.fragmentBtn2);



        setNewFragment(firstfragment);

        fragmentBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNewFragment(firstfragment);
            }
        });

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
        Button uploadBtn = findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,uploadphoto.class ));
            }
        });
    }
}