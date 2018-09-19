package com.example.asus.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswerTure;

    public Question(int textResId,boolean AnswerTrue){
        this.mAnswerTure=AnswerTrue;
        this.mTextResId=textResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTure() {
        return mAnswerTure;
    }

    public void setAnswerTure(boolean answerTure) {
        mAnswerTure = answerTure;
    }
}
