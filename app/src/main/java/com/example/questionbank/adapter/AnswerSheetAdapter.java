package com.example.questionbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.activity.DoQuestionActivity;

/**
 * @author cky
 * date 2019-10-11
 */
public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_sheet,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_question_num.setText(position+1+"");


    }


    @Override
    public int getItemCount() {
        return DoQuestionActivity.questionBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
         ImageView iv_right_or_wrong;
         TextView tv_question_num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_right_or_wrong = itemView.findViewById(R.id.iv_right_or_wrong);
            tv_question_num = itemView.findViewById(R.id.tv_question_num);
        }
    }
}
