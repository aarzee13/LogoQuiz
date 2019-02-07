package com.utili.aarzee.logoquiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ram on 2/24/2017.
 */

public class QuizAnswerAdapter extends RecyclerView.Adapter<QuizAnswerAdapter.MyViewHolder> {
    ArrayList<String> emptyAnswerArray;
    Context itemContext;
    LayoutInflater inflater;
    MyViewHolder viewHolder1;

    public QuizAnswerAdapter(Context mContext,List<String> emptyReceived){
        itemContext = mContext;
        inflater = LayoutInflater.from(mContext);

        emptyAnswerArray = new ArrayList<String>();
        emptyAnswerArray.addAll(emptyReceived);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (ImageView)itemView.findViewById(R.id.option_name);
        }
    }

    @Override
    public void onBindViewHolder(QuizAnswerAdapter.MyViewHolder holder, int position) {
        String imageAnswer = emptyAnswerArray.get(position);
        if("?".equals(imageAnswer)){
            //imageAnswer = "refresh";
            holder.title.setImageResource(R.drawable.refresh);
        }
        int d1 = itemContext.getResources().getIdentifier(imageAnswer,"drawable",itemContext.getPackageName());
        holder.title.setImageResource(d1);
            //holder.title.setText(emptyAnswerArray.get(position));
    }

    @Override
    public int getItemCount() {
        return emptyAnswerArray.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_answer, parent, false);
        viewHolder1 = new MyViewHolder(itemView1);
        return viewHolder1;

    }

}
