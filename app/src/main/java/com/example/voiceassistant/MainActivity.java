package com.example.voiceassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    protected Button sendButton;
    protected EditText userMessage;
    protected RecyclerView chatWindow;
    protected TextToSpeech tts;
    protected MessageController messageController;

    boolean hello = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        userMessage = findViewById(R.id.userMessage);
        chatWindow = findViewById(R.id.chatWindow);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(new Locale("en"));
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener();
            }
        });

        messageController = new MessageController();
        chatWindow.setLayoutManager(new LinearLayoutManager(this));
        chatWindow.setAdapter(messageController);


        if (hello) {
            helloMessage();
        }
    }


    protected void helloMessage() {
        userMessage.setText("greatings");
        onClickListener();
        hello = false;
    }

    protected void onClickListener() {
        String message = userMessage.getText().toString(); //User`s ask
        userMessage.setText(""); //field clean
        if (!hello) {
            messageController.messageList.add(new Message(message, true));//user message added
        }
        AI.getAnswer(message, new Consumer<String>() {
            @Override
            public void accept(String answer) {
                messageController.messageList.add(new Message(answer, false));
                tts.speak(answer, TextToSpeech.QUEUE_FLUSH, null, null);
                messageController.notifyDataSetChanged();
                chatWindow.scrollToPosition(messageController.messageList.size() - 1);
            }
        });
    }

}
