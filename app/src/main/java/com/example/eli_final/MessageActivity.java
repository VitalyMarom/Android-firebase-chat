package com.example.eli_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    private DatabaseReference mDatabase;
    private FirebaseUser user;


    private int messageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        user = FirebaseAuth.getInstance().getCurrentUser();

        final Button send = findViewById(R.id.send_msg);
        final EditText msg = findViewById(R.id.messagetxt);
        //final LinearLayout linearLayout = findViewById(R.id.msg_place);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final ArrayList<Chat_msg> msgList = new ArrayList<Chat_msg>();

        msgAdapter adpater = new msgAdapter(MessageActivity.this ,msgList);

        final ListView listview = findViewById(R.id.msg_list_view);
        listview.setAdapter(adpater);



        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.child("messages").getChildren()) {

                    String user = snapshot.child("user").getValue().toString();
                    String content = snapshot.child("content").getValue().toString();

                    Chat_msg tmpUser = new Chat_msg(user,content);
                    msgList.add(tmpUser);



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        final Query query = FirebaseDatabase.getInstance().getReference().child("messages").limitToLast(1);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String user = dataSnapshot.child("user").getValue().toString();
                String content = dataSnapshot.child("content").getValue().toString();

                Chat_msg tmpUser = new Chat_msg(user,content);
                msgList.add(tmpUser);

                updateList(msgList);

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

                mDatabase.child("messages").child(tempNum).child("user").setValue(user.getEmail());
                mDatabase.child("messages").child(tempNum).child("content").setValue(msg.getText().toString());

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

    private void updateList(ArrayList<Chat_msg> msgList) {

        msgAdapter adpater = new msgAdapter(MessageActivity.this ,msgList);

        ListView listview = findViewById(R.id.msg_list_view);
        listview.setAdapter(adpater);
        listview.setSelection(msgList.size()-1);

    }
}
