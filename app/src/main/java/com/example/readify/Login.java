package com.example.readify;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    TextView signUpText;
    TextView forgotPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpText=findViewById(R.id.signupText);
        forgotPassword=findViewById(R.id.forgot_password);

        mAuth=FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePassword() | !validateUsername()){

                }else{
                    String femail=email.getText().toString();
                    String fpass=password.getText().toString();
                    mAuth.signInWithEmailAndPassword(femail,fpass)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userUsername=email.getText().toString().trim();
                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                                        String userUsernam = email.getText().toString().trim();
                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                boolean userFound = false;

                                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                    String emailFromDB = userSnapshot.child("email").getValue(String.class);

                                                    if (emailFromDB != null && emailFromDB.equals(userUsernam)) {
                                                        userFound = true;

                                                        String nameFromDB = userSnapshot.child("name").getValue(String.class);
                                                        String usernameFromDB = userSnapshot.child("username").getValue(String.class);

                                                        Intent intent = new Intent(Login.this, HomeActivity.class);
                                                        intent.putExtra("name", nameFromDB);
                                                        intent.putExtra("email", emailFromDB);
                                                        intent.putExtra("username", usernameFromDB);
                                                        startActivity(intent);
                                                        break;
                                                    }
                                                }

                                                if (!userFound) {
                                                    email.setError("Invalid Email");
                                                    password.requestFocus();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e("FirebaseError", "Database error: " + error.getMessage());
                                            }
                                        });

                                    } else {
                                        password.setError("Invalid Credentials");
                                        email.setError("Authentication failed");
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });




                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public Boolean validateUsername(){
        String val=email.getText().toString();
        if(val.isEmpty()){
            email.setError("Email cannot be empty");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val=password.getText().toString();
        if(val.isEmpty()){
            password.setError("Password cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }
}