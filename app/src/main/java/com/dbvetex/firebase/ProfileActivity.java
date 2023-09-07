package com.dbvetex.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    ImageView imag1;
    TextView txt1;
    Button btn1;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imag1 = findViewById(R.id.imageview);
        txt1 = findViewById(R.id.textview);
        btn1 = findViewById(R.id.btnlogout);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            Glide.with(ProfileActivity.this).load(firebaseUser.getPhotoUrl()).into(imag1);
            txt1.setText(firebaseUser.getDisplayName());

        }
        googleSignInClient = GoogleSignIn.getClient(ProfileActivity.this,
                GoogleSignInOptions.DEFAULT_SIGN_IN);

        btn1.setOnClickListener(view -> {
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        firebaseAuth.signOut();
                        Toast.makeText(ProfileActivity.this, "LogOut SuccessFul", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        });


    }
}