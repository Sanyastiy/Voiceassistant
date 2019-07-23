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
            put("greatings", "hi \nmy name is Who \nand i`m fully english, my comrade");
            put("hi", "hello to you too");
            put("wyd", "Working on you, mate");
            put("how are you", "i`m great, thanks!");
            put("your name", "my name is Who");
            put("creates you", "Sasha");
            put("who is the president of Russia", "Look on TV, IDK");
            put("What color sky is", "It was blue at morning");
            put("Is it life on Mars", "Yeah, but It didn`t know about this");
            put("what language you speak", "i`m fully english");
        }};

        user_question = user_question.toLowerCase();

        final ArrayList<String> answers = new ArrayList<>();

        int max_score = 0;
        String max_score_answer = "ok";
        String[] split_user = user_question.split("\\s+");
        for (String database_question : dataBase.keySet()) {
            database_question = database_question.toLowerCase();
            String[] split_db = database_question.split("\\s+");

            int score = 0;
            for (String word_user : split_user) {
                for (String word_db : split_db) {
                    int max_len = Math.min(word_db.length(), word_user.length());
                    int cut_len = (int) (max_len * 0.7);
                    String word_user_cut = word_user.substring(0, cut_len);
                    String word_db_cut = word_db.substring(0, cut_len);
                    if (word_user_cut.equals(word_db_cut)) {
                        score++;
                    }
                }
            }
            if (score > max_score) {
                max_score = score;
                max_score_answer = dataBase.get(database_question);
            }
        }

        if (max_score > 0) {
            answers.add(max_score_answer);
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
