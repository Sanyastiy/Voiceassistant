package com.example.voiceassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends RecyclerView.Adapter {

    public List<Message> messageList = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.assistant_message, viewGroup, false);
        return new MessageView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int message_number) {
        Message message = messageList.get(message_number);
        ((MessageView) viewHolder).bind(message);
    }

    @Override
    public int getItemCount() {

        return messageList.size();
    }
}
