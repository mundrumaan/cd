package com.harish.e_voting;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by OO7 on 03/09/2015.
 */
public class PartyListAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    ArrayList<party_list_model> list;
    Context context;
    public PartyListAdapter(Context context, ArrayList<party_list_model> list){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup parent){
        View view;
        ViewHolder holder;
        if(contentView == null){
            view = mInflater.inflate(R.layout.party_list_item, parent, false);
            holder = new ViewHolder();
            holder.txtPName = (TextView) view.findViewById(R.id.pname);
            holder.txtCName = (TextView) view.findViewById(R.id.cname);
            holder.btnSubmitVote = (Button) view.findViewById(R.id.btnVote);
            view.setTag(holder);
        }else{
            view = contentView;
            holder = (ViewHolder) view.getTag();
        }
        party_list_model model = new party_list_model();
        model = (party_list_model) list.get(position);
        holder.txtPName.setText(model.getpName());
        holder.txtCName.setText(model.getcName());
        holder.btnSubmitVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseVoteClick(position);
            }
        });
        return view;
    }

    private void responseVoteClick(int pos){
        final String pid = list.get(pos).getPid();
        /*new MaterialDialog.Builder(context)
                .title("Confirm")
                .content("Are You Sure you want to Vote for " + list.get(pos).getcName() + "(" + list.get(pos).getpName() + ").")
                .negativeText("No")
                .positiveText("Yes")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        party_list.getInstance().userVoteClick(pid);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                        return;
                    }
                }).show();*/
        new AlertDialog.Builder(context)
                .setTitle("Confirm")
                .setMessage("Are You Sure you want to Vote?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        party_list.getInstance().userVoteClick(pid);
                    }
                })
                .show();
    }


    private class ViewHolder{
        public TextView txtPName, txtCName;
        public Button btnSubmitVote;
    }
}
