package com.example.readify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends DimActivity {

    EditText editName,editEmail,editUsername,editPassword;
    Button saveButton;
    String nameUser,emailUser,usernameUser,passwordUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reference= FirebaseDatabase.getInstance().getReference("Users");

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String updatedName = editName.getText().toString().trim();
            String updatedEmail = editEmail.getText().toString().trim();
            String updatedUsername = editUsername.getText().toString().trim();
            String updatedPassword = editPassword.getText().toString().trim();

            // Retrieve UID passed from intent
            String uid = getIntent().getStringExtra("uid");

            if (!updatedName.isEmpty() && !updatedEmail.isEmpty() && !updatedUsername.isEmpty() && !updatedPassword.isEmpty()) {
                updateProfile(uid, updatedName, updatedEmail, updatedUsername, updatedPassword);
            } else {
                Toast.makeText(EditProfile.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateProfile(String uid, String updatedName, String updatedEmail, String updatedUsername,String updatedPassword) {
        // Reference to the specific user node
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        // Create a map to update fields
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", updatedName);
        updates.put("email", updatedEmail);
        updates.put("username", updatedUsername);



        // Update the database
        reference.updateChildren(updates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                // Navigate back to the profile screen
                Intent intent = new Intent(EditProfile.this, ProfileActivity.class);
                intent.putExtra("name", updatedName);
                intent.putExtra("email", updatedEmail);
                intent.putExtra("username", updatedUsername);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(EditProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Log.e("FirebaseError", "Update failed: " + e.getMessage());
            Toast.makeText(EditProfile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


    private boolean isNameChanged(){
        if(!nameUser.equals(editName.getText().toString())){
            reference.child(usernameUser).child("name").setValue(editName.getText().toString());
            nameUser=editName.getText().toString();
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!emailUser.equals(editEmail.getText().toString())){
            reference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
            emailUser = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isUsernameChanged() {
        if (!passwordUser.equals(editUsername.getText().toString())){
            reference.child(usernameUser).child("password").setValue(editUsername.getText().toString());
            passwordUser = editUsername.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public void showData(){
        Intent intent=getIntent();
        nameUser = intent.getStringExtra("name");
        emailUser = intent.getStringExtra("email");
        usernameUser = intent.getStringExtra("username");

        editName.setText(nameUser);
        editEmail.setText(emailUser);
        editUsername.setText(usernameUser);
    }
}