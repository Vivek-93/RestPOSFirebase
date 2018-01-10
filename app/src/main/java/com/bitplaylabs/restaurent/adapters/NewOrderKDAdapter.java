package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.NewOrderList;
import com.bitplaylabs.restaurent.extra.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class NewOrderKDAdapter extends RecyclerView.Adapter<NewOrderKDAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchItemModel> mOrderList;


    public NewOrderKDAdapter(Context context, List<SearchItemModel> newOrderList) {
        this.mContext = context;
        this.mOrderList = newOrderList;

    }


    @Override
    public NewOrderKDAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_order_kd_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NewOrderKDAdapter.ViewHolder holder, final int position) {

       /* Log.d("NewOrderAdapter","name"+mOrderList.get(position).getKot().toString());
        holder.item_kot.setText(""+mOrderList.get(position).getKot().toString());*/
        holder.item_name.setText(mOrderList.get(position).getSearchItem().toString());
        holder.item_quantity.setText(""+mOrderList.get(position).getItemQuantity());
        holder.item_table.setText(mOrderList.get(position).getTableNo().toString());
       /* holder.item_order_time.setText(mOrderList.get(position).getOrder_time().toString());
        holder.item_time_elapsed.setText(mOrderList.get(position).getTime_elapsed().toString());*/
        holder.item_order_taken_by.setText(mOrderList.get(position).getCaptainName().toString());

    }


    @Override
    public int getItemCount() {
        Log.d("NewOrderAdapter", "" + mOrderList.size());
        return mOrderList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_kot, item_name, item_quantity, item_table, item_order_time, item_time_elapsed, item_order_taken_by;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            item_kot = (TextView) itemView.findViewById(R.id.fag_new_order_rv_kot_tv);
            item_name = (TextView) itemView.findViewById(R.id.fag_new_order_rv_item_name_tv);
            item_quantity = (TextView) itemView.findViewById(R.id.fag_new_order_rv_qty_tv);
            item_table = (TextView) itemView.findViewById(R.id.fag_new_order_rv_table_tv);
            item_order_time = (TextView) itemView.findViewById(R.id.fag_new_order_rv_order_time_tv);
            item_time_elapsed = (TextView) itemView.findViewById(R.id.fag_new_order_rv_time_elapsed_tv);
            item_order_taken_by = (TextView) itemView.findViewById(R.id.item_order_taken_by_tv);

        }
    }

}