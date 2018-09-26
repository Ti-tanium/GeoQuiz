package com.example.asus.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG="CheatActivity";
    private static final String KEY_IS_ANSWER_SHOWN="is answer shown";
    // 使用包名作为前缀，避免不同类的extra键名重复
    private static final String EXTRA_ANSWER_IS_TRUE="com.example.asus.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN="com.example.asus.geoquiz.answer_shown";


    private boolean mAnswerIsTrue;
    private boolean mIsAnswerShown;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent intent=new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(CheatActivity.RESULT_OK,data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mIsAnswerShown=savedInstanceState.getBoolean(KEY_IS_ANSWER_SHOWN);
            setAnswerShownResult(mIsAnswerShown);
        }
        setContentView(R.layout.activity_cheat);

        // get information from previous page (intent with the answer to the question)
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView=(TextView)findViewById(R.id.answer_text_view);
        mShowAnswerButton=(Button)findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(v -> {
            if(mAnswerIsTrue){
                mAnswerTextView.setText(R.string.true_button);
            }else{
                mAnswerTextView.setText(R.string.false_button);
            }
            mIsAnswerShown=true;
            setAnswerShownResult(mIsAnswerShown);

            int cx= mShowAnswerButton.getWidth()/2;
            int cy=mShowAnswerButton.getHeight()/2;
            float radius=mShowAnswerButton.getWidth();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstanceState called");
        outState.putBoolean(KEY_IS_ANSWER_SHOWN,mIsAnswerShown);
    }

}
