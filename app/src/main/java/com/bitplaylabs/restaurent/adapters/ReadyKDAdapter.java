package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.SearchItemModel;

import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class ReadyKDAdapter extends RecyclerView.Adapter<ReadyKDAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchItemModel> mReadyList;


    public ReadyKDAdapter(Context context, List<SearchItemModel> mReadyList) {
        this.mContext = context;
        this.mReadyList = mReadyList;

    }


    @Override
    public ReadyKDAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ready_order_kd_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ReadyKDAdapter.ViewHolder holder, int position) {

       /* Log.d("NewOrderAdapter","name"+mOrderList.get(position).getKot().toString());
        holder.item_kot.setText(""+mOrderList.get(position).getKot().toString());*/
        holder.item_ready_name.setText(mReadyList.get(position).getSearchItem().toString());
        holder.item_ready_quantity.setText(""+mReadyList.get(position).getItemQuantity());
        holder.item_ready_table.setText(mReadyList.get(position).getTableNo().toString());
        holder.item_ready_order_time.setText(mReadyList.get(position).getTime().toString());
     //   holder.item_time_elapsed.setText(mOrderList.get(position).getTime_elapsed().toString());
        holder.item_ready_order_taken_by.setText(mReadyList.get(position).getCaptainName().toString());



    }

    @Override
    public int getItemCount() {

        Toast.makeText(mContext, ""+mReadyList.size(), Toast.LENGTH_SHORT).show();
        return mReadyList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_ready_kot, item_ready_name, item_ready_quantity, item_ready_table, item_ready_order_time, item_ready_time_elapsed, item_ready_order_taken_by;


        public ViewHolder(View itemView) {
            super(itemView);

            item_ready_kot = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_kot_tv);
            item_ready_name = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_item_name_tv);
            item_ready_quantity = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_qty_tv);
            item_ready_table = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_table_tv);
            item_ready_order_time = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_order_time_tv);
            item_ready_time_elapsed = (TextView) itemView.findViewById(R.id.fag_ready_order_rv_time_elapsed_tv);
            item_ready_order_taken_by = (TextView) itemView.findViewById(R.id.item_ready_order_taken_by_tv);

        }
    }

}