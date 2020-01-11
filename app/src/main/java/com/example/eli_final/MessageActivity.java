package com.example.eli_final;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseUser user;


    private int messageNumber=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        final Button send = findViewById(R.id.send_msg);
        final EditText msg = findViewById(R.id.messagetxt);
        final TextView txt =findViewById(R.id.test_txt);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempNum = Integer.toString(messageNumber);
                tempNum="message"+tempNum;

                messageNumber++;

                mDatabase.child("messsages").child(tempNum).child("user").setValue(user.getUid());
                mDatabase.child("messsages").child(tempNum).child("content").setValue(msg.getText().toString());
            }
        });

        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        txt.setText(txt.getText().toString() + snap.getValue() + "\n");
                    }



                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //String temp = "";


    }
}
