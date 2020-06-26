package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DisplayActivity extends AppCompatActivity {

    TextView displaySender, displayCreatedAt, displaySubject, displayMessage;
    Button displayFinishButton;
    InboxData inboxData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Email");
        inboxData = new InboxData();
        inboxData = (InboxData) getIntent().getSerializableExtra("inboxData");

        displaySender = findViewById(R.id.displaySender);
        displayCreatedAt = findViewById(R.id.displayCreatedAt);
        displaySubject = findViewById(R.id.displaySubject);
        displayMessage = findViewById(R.id.displayMessage);
        displayFinishButton = findViewById(R.id.displayFinishButton);

        displaySender.setText(inboxData.senderName);
        displaySubject.setText(inboxData.subject);
        displayMessage.setText(inboxData.message);
        displayCreatedAt.setText(inboxData.dateTime);

        displayFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
