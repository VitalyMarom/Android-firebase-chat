package com.example.eli_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String toast = dataSnapshot.toString();

                Toast t = Toast.makeText(MessageActivity.this,toast,Toast.LENGTH_LONG);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

        final Button send = findViewById(R.id.send_msg);
        final EditText msg = findViewById(R.id.messagetxt);
        final TextView txt =findViewById(R.id.test_txt);
        final LinearLayout linearLayout = findViewById(R.id.msg_place);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempNum = Integer.toString(messageNumber);
                tempNum="message"+tempNum;

                messageNumber++;

                mDatabase.child("messsages").child(tempNum).child("user").setValue(user.getEmail());
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
                        TextView msg_txt = new TextView(MessageActivity.this);
                        String txtForView = snap.child("user").getValue().toString();
                        txtForView += " : " + snap.child("content").getValue().toString();

                        msg_txt.setText(txtForView);

                        linearLayout.addView(msg_txt);
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
