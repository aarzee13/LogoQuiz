package com.utili.aarzee.logoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    Context grpContext;
    RecyclerView grpRecyclerView;
    RecyclerView.LayoutManager grpRecyclerViewLayoutManager;
    GroupAdapter grpAdapter;
    ArrayList<Group_Model> grp_list;
    ArrayList<Item_Model> success_list;
    SqliteController sqlt;
    Integer tSuccess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        sqlt = new SqliteController(getApplicationContext());
        grp_list = sqlt.getGroup();
        success_list = sqlt.getSuccessList();
        for(Item_Model successList:success_list){
            tSuccess += successList.getCountSuccess();
        }

        grpContext = getApplicationContext();
        grpRecyclerView = (RecyclerView) findViewById(R.id.list_recyclerview);
        grpRecyclerViewLayoutManager = new LinearLayoutManager(grpContext);
        grpRecyclerView.setLayoutManager(grpRecyclerViewLayoutManager);
        grpAdapter = new GroupAdapter(grpContext,grp_list,success_list);
        grpRecyclerView.setAdapter(grpAdapter);

        grpRecyclerView.addOnItemTouchListener( new RecyclerItemClickListener(grpContext,grpRecyclerView, new RecyclerItemClickListener.OnItemClickListener(){
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        if(position == 0){
                            showDetail(position);
                        }
                        else if(position == 1){
                            showDetail(position);
                        }
                        else if(position == 2){
                            if(tSuccess <50){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 3){
                            if(tSuccess <70){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 4){
                            if(tSuccess <90){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 5){
                            if(tSuccess <115){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 6){
                            if(tSuccess <135){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 7){
                            if(tSuccess <150){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 8){
                            if(tSuccess <175){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 9){
                            if(tSuccess <200){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 10){
                            if(tSuccess <220){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 11){
                            if(tSuccess <240){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 12){
                            if(tSuccess <260){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 13){
                            if(tSuccess <285){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 14){
                            if(tSuccess <310){
                            }
                            else{
                                showDetail(position);
                            }
                        }
                        else if(position == 15){
                            if(tSuccess <350){
                            }
                            else{
                                showDetail(position);
                            }
                        }


//                        String grpFilter = grp_list.get(position).getGrp_id();
//                        Intent detailI = new Intent(GroupActivity.this,ItemActivity.class);
//                        detailI.putExtra("grpFilter",grpFilter);
//                        startActivity(detailI);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    public void showDetail(Integer po){
        String grpFilter = grp_list.get(po).getGrp_id();
//        Intent detailI = new Intent(GroupActivity.this,ItemActivity.class);
//        detailI.putExtra("grpFilter",grpFilter);
//        startActivity(detailI);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(this,HomeActivity.class);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                Intent mBack = new Intent(GroupActivity.this,HomeActivity.class);
                startActivity(mBack);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
