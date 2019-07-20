package com.example.voiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText userMessage;
    protected RecyclerView chatWindow;

    protected MessageController messageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener();
            }
        });

        messageController = new MessageController();
        chatWindow.setLayoutManager(new LinearLayoutManager(this));
        chatWindow.setAdapter(messageController);
    }

    protected void onClickListener() {
        String message = userMessage.getText().toString(); //User`s ask
        userMessage.setText(""); //field clean

        messageController.messageList.add(new Message(message, true));
        //chatWindow.append("\n>> " + message);
        String answer = AI.getAnswer(message);
        //chatWindow.append("\n<< " + answer);

        messageController.messageList.add(new Message(answer, true));

        messageController.notifyDataSetChanged();
        chatWindow.scrollToPosition(messageController.messageList.size() - 1);
    }
}
