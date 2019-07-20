package com.example.voiceassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AI {
    public static String getAnswer(String user_question) {

        Map<String, String> dataBase = new HashMap<String, String>() {{
            put("hi", "hello to you too");
            put("wyd", "Working on you, mate");
            put("how are you", "i`m great, thanks!");
            put("your name", "my name is Who");
            put("creates you", "Sasha");
        }};

        user_question = user_question.toLowerCase();

        ArrayList<String> answers = new ArrayList<>();

        for (String database_question : dataBase.keySet()) {
            if (user_question.contains(database_question)) {
                answers.add(dataBase.get(database_question));
            }
        }

        if (answers.isEmpty()) {
            return "ok";
        } else {
            return String.join(", ", answers);
        }

    }
}
