package com.example.asus.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import javax.net.ssl.KeyManagerFactorySpi;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;
    private boolean mIsCheater;
    private TextView mQuestionTextView;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT=0;
    private static final String KEY_IS_CHEATER="isCheater";
    private static final String KEY_IS_QUESTION_ANSWERED="IsQuestionAnswered";
    private static final String KEY_NUMBER_OF_RIGHT_ANSWER="NumberOfRightAnwswer";
    private boolean[] mIsQuestionAnswered;
    private int mNumberOfRightAnwswer;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true)
    };
    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater=savedInstanceState.getBoolean(KEY_IS_CHEATER);
            mNumberOfRightAnwswer=savedInstanceState.getInt(KEY_NUMBER_OF_RIGHT_ANSWER);
            mIsQuestionAnswered=savedInstanceState.getBooleanArray(KEY_IS_QUESTION_ANSWERED);
        }else{
            mIsQuestionAnswered=new boolean[mQuestionBank.length];
            for(int i=0;i<mQuestionBank.length;i++){
                mIsQuestionAnswered[i]=false;
            }
            mNumberOfRightAnwswer=0;
        }

        setContentView(R.layout.activity_quiz);

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener((View view) -> {
            boolean answerIsTrue=mQuestionBank[mCurrentIndex].isAnswerTure();
            Intent intent=CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
            startActivityForResult(intent,REQUEST_CODE_CHEAT);
        });

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(v -> checkAnswer(true));

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(v -> checkAnswer(false));

        if(mIsQuestionAnswered[mCurrentIndex]){
            setButtonStatus(false);
        }else{
            setButtonStatus(true);
        }

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
            int question1 = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question1);
            mIsCheater=false;
            if(mIsQuestionAnswered[mCurrentIndex]){
                setButtonStatus(false);
            }else{
                setButtonStatus(true);
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(v -> {
            mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
            int question12 = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question12);
            if(mIsQuestionAnswered[mCurrentIndex]){
                setButtonStatus(false);
            }else{
                setButtonStatus(true);
            }
        });
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean isAnswerTrue = mQuestionBank[mCurrentIndex].isAnswerTure();
        int messageResId = 0;
        if(mIsCheater==true){
            messageResId=R.string.judgement_toast;
        }else{
            if (userPressedTrue == isAnswerTrue) {
                messageResId = R.string.correct_text;
                mNumberOfRightAnwswer++;
            } else {
                messageResId = R.string.incorrect_text;
            }
        }
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
        mIsQuestionAnswered[mCurrentIndex]=true;
        setButtonStatus(false);
        if(mCurrentIndex==mQuestionBank.length-1){
            double score=mNumberOfRightAnwswer*100/mQuestionBank.length;
            Toast.makeText(QuizActivity.this,"Total Score:"+score+"%",Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonStatus(boolean enabled){
        mTrueButton.setEnabled(enabled);
        mFalseButton.setEnabled(enabled);
        mCheatButton.setEnabled(enabled);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode!=RESULT_OK)
            return;
        if(requestCode==REQUEST_CODE_CHEAT){
            if(data==null)
                return;
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_IS_CHEATER,mIsCheater);
        outState.putBooleanArray(KEY_IS_QUESTION_ANSWERED,mIsQuestionAnswered);
        outState.putInt(KEY_NUMBER_OF_RIGHT_ANSWER,mNumberOfRightAnwswer);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
