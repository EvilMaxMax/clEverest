package ru.myitschool.cleverest;

public class Question {
    String question;
    String[] answer = new String[4];
    Question(String ... s){
        question = s[0];
        answer[0] = s[1];
        answer[1] = s[2];
        answer[2] = s[3];
        answer[3] = s[4];

    }
}
