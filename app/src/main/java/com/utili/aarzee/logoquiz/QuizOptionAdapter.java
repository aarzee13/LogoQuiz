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

public class QuizOptionAdapter extends RecyclerView.Adapter<QuizOptionAdapter.MyViewHolder> {
    ArrayList<String> itemOptionArray;
    private List<String> optionList = null;
//public int incre = 0;
    Context itemContext;
    LayoutInflater inflater;
    MyViewHolder viewHolder1;

    public QuizOptionAdapter(Context mContext, List<String> itemOptionReceived){
        itemContext = mContext;
        optionList = itemOptionReceived;
        inflater = LayoutInflater.from(mContext);
        itemOptionArray = new ArrayList<String>();
        itemOptionArray.addAll(itemOptionReceived);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (ImageView)itemView.findViewById(R.id.option_name);
        }
    }

    @Override
    public void onBindViewHolder(QuizOptionAdapter.MyViewHolder holder, int position) {
        String imageOption = itemOptionArray.get(position);
        int d1 = itemContext.getResources().getIdentifier(imageOption,"drawable",itemContext.getPackageName());
        holder.title.setImageResource(d1);
            //holder.title.setText(itemOptionArray.get(position));
            //holder.title.setImageResource(R.drawable.a);
    }

    @Override
    public int getItemCount() {
        return itemOptionArray.size();
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
                .inflate(R.layout.cards_options, parent, false);
        viewHolder1 = new MyViewHolder(itemView1);
        return viewHolder1;

    }
}
