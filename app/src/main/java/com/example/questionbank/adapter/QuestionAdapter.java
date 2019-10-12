package com.example.questionbank.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.questionbank.activity.DoQuestionActivity;
import com.example.questionbank.fragment.DoQuestionFragment;

/**
 * @author cky
 * date 2019-10-11
 */
public class QuestionAdapter extends FragmentStatePagerAdapter {

    public QuestionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d("getItem",position+"");
        return new DoQuestionFragment(position);
    }

    @Override
    public int getCount() {
        return DoQuestionActivity.questionBeanList.size();
    }
}
