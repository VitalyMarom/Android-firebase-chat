package com.example.eli_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        ImageView a1 = findViewById(R.id.a1);
        ImageView a2 = findViewById(R.id.a2);
        ImageView a3 = findViewById(R.id.a3);
        ImageView a4 = findViewById(R.id.a4);
        ImageView a5 = findViewById(R.id.a5);

        final Intent intent = new Intent(this,MessageActivity.class);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("imgId",R.drawable.a1);
                startActivity(intent);
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("imgId",R.drawable.a2);
                startActivity(intent);
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("imgId",R.drawable.a3);
                startActivity(intent);
            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("imgId",R.drawable.a4);
                startActivity(intent);
            }
        });

        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("imgId",R.drawable.a5);
                startActivity(intent);
            }

        });


    }
}
