package com.utili.aarzee.logoquiz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ram on 2/24/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    ArrayList<Item_Model> itemListArray;
    private  List<Item_Model> itemList = null;
    Context itemContext;
    LayoutInflater inflater;
    MyViewHolder viewHolder1;

    public ItemAdapter(Context mContext, List<Item_Model> itemListReceived){
        itemContext = mContext;
        itemList = itemListReceived;
        inflater = LayoutInflater.from(mContext);
        itemListArray = new ArrayList<Item_Model>();
        itemListArray.addAll(itemListReceived);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //TextView title1;
        ImageView imgView;
        ImageView correctView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView)itemView.findViewById(R.id.detail_img);
            correctView = (ImageView)itemView.findViewById(R.id.detail_correct_img);
            //title1 = (TextView)itemView.findViewById(R.id.detail_name);
        }
    }

    @Override
    public void onBindViewHolder(ItemAdapter.MyViewHolder holder, int position) {
        Item_Model name = itemListArray.get(position);

        if("success".equals(name.getResult().toString())){
            String imageName = name.getItem_name().replace(' ','_');
            int d = itemContext.getResources().getIdentifier(imageName,"drawable",itemContext.getPackageName());
            holder.imgView.setImageResource(d);
            holder.imgView.setAlpha(0.2f);
            int co = itemContext.getResources().getIdentifier("correct","drawable",itemContext.getPackageName());
            holder.correctView.setImageResource(co);
        }
        else{
            String imageName = name.getItem_name().replace(' ','_');
            int d = itemContext.getResources().getIdentifier(imageName,"drawable",itemContext.getPackageName());
            holder.imgView.setImageResource(d);
        }
    }

    @Override
    public int getItemCount() {
        return itemListArray.size();
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
                .inflate(R.layout.cards_item, parent, false);

        Animation animation = AnimationUtils.loadAnimation(parent.getContext(),R.anim.push_left_in);
        animation.setDuration(1000);
        itemView1.startAnimation(animation);
        viewHolder1 = new MyViewHolder(itemView1);
        return viewHolder1;

    }
}
