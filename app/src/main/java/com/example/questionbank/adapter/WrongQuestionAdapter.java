package com.example.questionbank.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.questionbank.R;
import com.example.questionbank.bean.QuestionAnswerBean;
import com.example.questionbank.bean.QuestionBean;
import com.example.questionbank.utils.ItemSlideHelper;

import java.util.List;

/**
 * @author cky
 * date 2019-10-12
 */
public class WrongQuestionAdapter extends RecyclerView.Adapter<WrongQuestionAdapter.ViewHolder> implements ItemSlideHelper.Callback{
    List<QuestionBean> wrongQuestionList;
    private RecyclerView mRecyclerView;
    private WrongQuestionAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(WrongQuestionAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public WrongQuestionAdapter(List<QuestionBean> wrongQuestionList) {
        this.wrongQuestionList = wrongQuestionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wrong_question,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionBean questionBean = wrongQuestionList.get(position);
        holder.tv_wrong_question_title.setText(questionBean.getId()+"、"+questionBean.getQuestion());

        //删除功能
        holder.iv_message_delete.setOnClickListener(v -> {
            wrongQuestionList.remove(position);
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return wrongQuestionList.size();
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        if (holder.itemView instanceof ConstraintLayout) {
            ViewGroup viewGroup = (ViewGroup) holder.itemView;
            return viewGroup.getChildAt(0).getLayoutParams().width
                    + viewGroup.getChildAt(1).getLayoutParams().width;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        return mRecyclerView.getChildViewHolder(childView);
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mRecyclerView.getContext(), this));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_wrong_question_title;
        ImageView iv_message_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_wrong_question_title = itemView.findViewById(R.id.tv_wrong_question_title);
            iv_message_delete = itemView.findViewById(R.id.iv_message_delete);
        }
    }
}
