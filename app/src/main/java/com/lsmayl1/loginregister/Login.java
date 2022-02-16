package com.lsmayl1.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://loginregister-791f2-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText phone =findViewById(R.id.phone);
        final EditText password =findViewById(R.id.password);
        final Button loginBtn =findViewById(R.id.loginBtn);
        final TextView registerNowBtn =findViewById(R.id.registerNowBtn);
        final CheckBox remember = findViewById(R.id.rememberme);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
         String checkbox = preferences.getString("remember", "");
         if (checkbox.equals("true")){
           Intent intent =  new Intent(Login.this, MainActivity.class);
           startActivity(intent);

         }else if(checkbox.equals("false")){
             Toast.makeText(Login.this, "Please registr", Toast.LENGTH_SHORT).show();
         }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String phoneTxt = phone.getText().toString();
                final String PasswordTxt = password.getText().toString();

                if(phoneTxt.isEmpty() || PasswordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your mobile or password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneTxt)){

                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                if(getPassword.equals(PasswordTxt)){
                                    Toast.makeText(Login.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open Register Activity
                startActivity(new Intent(Login.this,Register.class ));
            }
        });


        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(Login.this, "Checked",Toast.LENGTH_SHORT  ).show();
                }else  if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(Login.this, "Unchecked",Toast.LENGTH_SHORT  ).show();

                }
            }
        });
    }



}