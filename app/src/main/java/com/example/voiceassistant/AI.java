package com.example.voiceassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    public static void getAnswer(String user_question, final Consumer<String> callback) {

        Map<String, String> dataBase = new HashMap<String, String>() {{
            put("hi", "hello to you too");
            put("wyd", "Working on you, mate");
            put("how are you", "i`m great, thanks!");
            put("your name", "my name is Who");
            put("creates you", "Sasha");
        }};

        user_question = user_question.toLowerCase();

        final ArrayList<String> answers = new ArrayList<>();

        for (String database_question : dataBase.keySet()) {
            if (user_question.contains(database_question)) {
                answers.add(dataBase.get(database_question));
            }
        }

        Pattern cityPattern =
                Pattern.compile("weather in (\\p{L}+)",
                        Pattern.CASE_INSENSITIVE);
        Matcher matcher = cityPattern.matcher(user_question);
        if (matcher.find()) {
            String cityName = matcher.group(1);
            Weather.get(cityName, new Consumer<String>() {
                @Override
                public void accept(String s) {
                    answers.add(s);
                    callback.accept(String.join(", ", answers));
                }
            });
        } else {
            if (answers.isEmpty()) {
                callback.accept("Ok");
                return;
            }
            callback.accept(String.join(", ", answers));
        }

    }
}
