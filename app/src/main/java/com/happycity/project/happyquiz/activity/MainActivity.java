package com.happycity.project.happyquiz.activity;
/*
* Author: Ha Truong _ Happy City
* Role: Software engineer
* */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.happycity.project.happyquiz.R;
import com.happycity.project.happyquiz.model.User;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNewUserName, edtNewPassWord, edtNewEmail;
    MaterialEditText edtUserName, edtPassWord;

    Button btnSignUp, btnSignIn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;

    View sign_up_layout;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFirebaseDatabase();
        initViewSignIn();
        addEventToButton();
    }

    private void addEventToButton() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignUpDialog();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edtUserName.getText().toString(), edtPassWord.getText().toString());
            }
        });
    }

    private void signIn(final String user, final String passWord) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists()){
                    if (!user.isEmpty()){
                        User login = dataSnapshot.child(user).getValue(User.class);
                        if (login.getPassWord().equals(passWord)){
                            Toast.makeText(MainActivity.this, "Login Ok", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }else{
                            Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Please enter user name", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "User is not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showSignUpDialog() {
        final AlertDialog.Builder aleartDialog = new AlertDialog.Builder(MainActivity.this);
        aleartDialog.setTitle("Sign up");
        aleartDialog.setMessage("Please fill up information");
        initViewLayoutSignUp();
        initViewSignUp();
        aleartDialog.setView(sign_up_layout);
        aleartDialog.setIcon(R.drawable.ic_account_circle_black_24dp);
        aleartDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aleartDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signUpToDatabase();
            }
        });
        aleartDialog.show();
    }

    private void signUpToDatabase(){
        final User user = new User(edtNewUserName.getText().toString(),
                edtNewPassWord.getText().toString(),
                edtNewEmail.getText().toString());
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUserName()).exists()){
                    Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }else{
                    users.child(user.getUserName()).setValue(user);
                    Toast.makeText(MainActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initViewLayoutSignUp(){
        inflater = this.getLayoutInflater();
        sign_up_layout = inflater.inflate(R.layout.sign_up_layout, null);
    }
    private void initViewSignUp() {
        edtNewUserName = (MaterialEditText) sign_up_layout.findViewById(R.id.editNewUserName);
        edtNewEmail = (MaterialEditText) sign_up_layout.findViewById(R.id.editNewEmail);
        edtNewPassWord = (MaterialEditText) sign_up_layout.findViewById(R.id.editNewPassword);
    }

    private void initViewSignIn() {
        edtUserName = (MaterialEditText) findViewById(R.id.editUserName);
        edtPassWord = (MaterialEditText) findViewById(R.id.editPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    private void setupFirebaseDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");
    }
}
