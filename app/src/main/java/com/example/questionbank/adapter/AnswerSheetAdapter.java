package com.example.questionbank.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.activity.DoQuestionActivity;
import com.example.questionbank.bean.QuestionAnswerBean;
import com.example.questionbank.fragment.DoQuestionFragment;

import java.util.List;

/**
 * @author cky
 * date 2019-10-11
 */
public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.ViewHolder> {
    private JumpViewpager jumpViewpager;
    List<QuestionAnswerBean> questionAnswerBeanList;

    public void setJumpViewpager(JumpViewpager jumpViewpager) {
        this.jumpViewpager = jumpViewpager;
    }

    public interface JumpViewpager{
        void jump(int position);
    }


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
        holder.tv_question_num.setTextColor(Color.parseColor("#000000"));
        questionAnswerBeanList = DoQuestionActivity.questionAnswerBeanList;
        QuestionAnswerBean questionAnswerBean = questionAnswerBeanList.get(position);

        if (questionAnswerBean.getAnswerStatus() == 0){
            holder.iv_right_or_wrong.setImageResource(R.mipmap.circle_wrong);
        }else if (questionAnswerBean.getAnswerStatus() == 1){
            holder.iv_right_or_wrong.setImageResource(R.mipmap.circle_write);
        }

        holder.itemView.setOnClickListener(v -> {
            if (jumpViewpager != null){
                jumpViewpager.jump(position);
            }
        });
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
