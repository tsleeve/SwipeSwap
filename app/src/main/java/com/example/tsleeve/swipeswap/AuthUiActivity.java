package com.example.tsleeve.swipeswap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AuthUiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 100;
    ProgressBar spinner;
    Button buttonSignIn;
    private UserAuth mUAuth = new UserAuth();
    private SwipeDataAuth db = new SwipeDataAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.auth_ui_layout);
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Got Swipes?");

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);
        buttonSignIn.setText("Login");

        //FirebaseAuth auth = FirebaseAuth.getInstance();
        if (mUAuth.validUser()) {
        //if (auth.getCurrentUser() != null) {
            startActivity(MainActivity.createIntent(this));
            finish();
        }

        //setContentView(R.layout.auth_ui_layout);
        //mRootView = findViewById(R.id.content_main);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }
    }

    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final String uid = mUAuth.uid();
            //final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference ref = db.getUsersReference();
            //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(uid)) {
                        startActivity(new Intent(AuthUiActivity.this, RegisterActivity.class));
                        finish();
                    } else {
                        startActivity(MainActivity.createIntent(AuthUiActivity.this));
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            finish();
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder()
                            .build(),
                    RC_SIGN_IN);
            return;
        }

    }

    public static Intent createIntent(Context context) {
        Intent in = new Intent();
        in.setClass(context, AuthUiActivity.class);
        return in;
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .build(),
                RC_SIGN_IN);
        buttonSignIn.setVisibility(View.INVISIBLE);
    }
}
