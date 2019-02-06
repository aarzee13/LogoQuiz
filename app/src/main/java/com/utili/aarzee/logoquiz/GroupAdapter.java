package com.utili.aarzee.logoquiz;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ram on 2/24/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    ArrayList<Group_Model> grpListArray;
    ArrayList<Item_Model> successListArray;

    Context context;
    LayoutInflater inflater;
    MyViewHolder viewHolder1;

    public GroupAdapter(Context mContext, List<Group_Model> grpListReceived, List<Item_Model> successListReceived){
        context = mContext;
        inflater = LayoutInflater.from(mContext);
        grpListArray = new ArrayList<Group_Model>();
        grpListArray.addAll(grpListReceived);

        successListArray = new ArrayList<Item_Model>();
        successListArray.addAll(successListReceived);

//        Animation animation = AnimationUtils.loadAnimation(someContext, R.anim.swing_up_left);
//        yourView.startAnimation(animation);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView completed;
        TextView hanged;
        TextView rem;
        ProgressBar pBar;
        CardView listCard;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.list_name);
            completed = (TextView)itemView.findViewById(R.id.list_completed);
            hanged = (TextView)itemView.findViewById(R.id.list_hanged);
            pBar = (ProgressBar)itemView.findViewById(R.id.determinateBar);
            listCard = (CardView)itemView.findViewById(R.id.list_card);

        }
    }

    @Override
    public void onBindViewHolder(GroupAdapter.MyViewHolder holder, int position) {
        Integer totalSuccess=0;
        for(Item_Model gs:successListArray){
            totalSuccess += gs.getCountSuccess();
        }

        if(position == 0){
            showGroup(holder,position);
        }
        else if(position == 1){
            showGroup(holder,position);
        }
        else if(position == 2){
            if(totalSuccess <50){
                disableClick(holder,50);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 3){
            if(totalSuccess <70){
                disableClick(holder,70);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 4){
            if(totalSuccess <90){
                disableClick(holder,90);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 5){
            if(totalSuccess <115){
                disableClick(holder,115);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 6){
            if(totalSuccess <135){
                disableClick(holder,135);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 7){
            if(totalSuccess <150){
                disableClick(holder,150);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 8){
            if(totalSuccess <175){
                disableClick(holder,175);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 9){
            if(totalSuccess <200){
                disableClick(holder,200);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 10){
            if(totalSuccess <220){
                disableClick(holder,220);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 11){
            if(totalSuccess <240){
                disableClick(holder,240);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 12){
            if(totalSuccess <260){
                disableClick(holder,260);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 13){
            if(totalSuccess <285){
                disableClick(holder,285);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 14){
            if(totalSuccess <310){
                disableClick(holder,310);
            }
            else{
                showGroup(holder,position);
            }
        }
        else if(position == 15){
            if(totalSuccess <350){
                disableClick(holder,350);
            }
            else{
                showGroup(holder,position);
            }
        }

//        Group_Model name = grpListArray.get(position);
//        holder.title.setText(name.getGrp_name());
//        for(Item_Model st: successListArray){
//            if(name.getGrp_id().equals(st.getGrp_id())){
//                holder.completed.setText("Completed \n"+ st.getCountSuccess().toString()+"/"+st.getCountTotal().toString());
//                holder.hanged.setText("Hanged \n"+ st.getCountHanged().toString()+"/"+st.getCountTotal().toString());
//                holder.pBar.setProgress((st.getCountSuccess()*100)/st.getCountTotal());
//
//            }
//        }
    }

    public void showGroup(GroupAdapter.MyViewHolder ho,Integer k){
        Group_Model name = grpListArray.get(k);
        for(Item_Model sl: successListArray){
            if(name.getGrp_id().equals(sl.getGrp_id())){
                ho.title.setText(name.getGrp_name());
                ho.completed.setText("Completed \n" + sl.getCountSuccess().toString() + "/" + sl.getCountTotal().toString());
                ho.hanged.setText("Hanged \n" + sl.getCountHanged().toString() + "/" + sl.getCountTotal().toString());
                ho.pBar.setProgress((sl.getCountSuccess() * 100) / sl.getCountTotal());
            }
        }
    }

    public void disableClick(GroupAdapter.MyViewHolder hol,Integer ina){
        hol.title.setText("Answer " +ina+" Logos to unlock this group");
        hol.listCard.setBackgroundResource(R.drawable.tags_rounded_corner);
        hol.pBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return grpListArray.size();
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
                .inflate(R.layout.cards_list, parent, false);


        Animation animation = AnimationUtils.loadAnimation(parent.getContext(),R.anim.swing_up_right);
        animation.setDuration(1000);
        itemView1.startAnimation(animation);
        viewHolder1 = new MyViewHolder(itemView1);
        return viewHolder1;

    }

}
