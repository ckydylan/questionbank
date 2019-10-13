package com.example.questionbank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.db.QuestionDAO;
import com.example.questionbank.utils.ItemSlideHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cky
 * date 2019-10-12
 * 错题
 */
public class AllQuestionAdapter extends RecyclerView.Adapter<AllQuestionAdapter.ViewHolder>{
    List<QuestionBean> allQuestionList;
    private OnItemClickListener onItemClickListener = null;

    public void setList(List<QuestionBean> allQuestionList){
        this.allQuestionList = allQuestionList;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public AllQuestionAdapter(List<QuestionBean> allQuestionList) {
        this.allQuestionList = allQuestionList;
        Log.d("allQuestionList",allQuestionList.toString());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_question,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionBean questionBean = allQuestionList.get(position);
        holder.tv_all_question_title.setText(questionBean.getQuestion());
        holder.tv_do_times.setText("做过："+questionBean.getTesttime()+"次");
        holder.tv_wrong_times.setText("错误："+questionBean.getWrongtime()+"次");

        float rightPer = (float) (questionBean.getTesttime()-questionBean.getWrongtime())/questionBean.getTesttime();
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        String format = nt.format(rightPer);

        if (format.contains("NaN")){
            holder.tv_right_per.setText("正确率：0%");
        }else {
            holder.tv_right_per.setText("正确率："+format);
        }


        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return allQuestionList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_all_question_title,tv_do_times,tv_wrong_times,tv_right_per;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_all_question_title = itemView.findViewById(R.id.tv_all_question_title);
            tv_do_times = itemView.findViewById(R.id.tv_do_times);
            tv_wrong_times = itemView.findViewById(R.id.tv_wrong_times);
            tv_right_per = itemView.findViewById(R.id.tv_right_per);
        }
    }
}
