package com.example.eli_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private FirebaseUser user;


    private int messageNumber;
    private int UserImg = 1;
    ArrayList<Chat_msg> msgList = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance().getReference().child("messages").limitToLast(1);

        Intent intent = getIntent();
        UserImg = intent.getIntExtra("imgId",0);

        final Button send = findViewById(R.id.send_msg);
        final EditText msg = findViewById(R.id.messagetxt);
        final ListView listview = findViewById(R.id.msg_list_view);

        msgAdapter adapter = new msgAdapter(MessageActivity.this ,msgList);
        listview.setAdapter(adapter);


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.child("messages").getChildren()) {

                    Chat_msg tmpUser = snapshot.getValue(Chat_msg.class);

                    msgList.add(tmpUser);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Chat_msg tmpUser = dataSnapshot.getValue(Chat_msg.class);

                msgList.add(tmpUser);

                listview.setSelection(msgList.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //updateList(msgList);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tempNum = Integer.toString(messageNumber);
                //tempNum="message"+tempNum;

                messageNumber++;

                Chat_msg temp=new Chat_msg(user.getEmail(),msg.getText().toString(),UserImg);

                mDatabase.child("messages").child(tempNum).setValue(temp);

                //set new message number
                mDatabase.child("messageNumber").setValue(messageNumber);

                msg.setText("");
            }
        });

        //final DatabaseReference keyDBPath = FirebaseDatabase.getInstance().getReference().child("messageNumber");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageNumber = Integer.parseInt(dataSnapshot.child("messageNumber").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
