package com.developernca.gkformm.bean;

/**
 * Created on 8/17/2018.
 *
 * @author Nyein Chan Aung
 */

public class Details {

    private String question;
    private String answer;

    public Details(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
