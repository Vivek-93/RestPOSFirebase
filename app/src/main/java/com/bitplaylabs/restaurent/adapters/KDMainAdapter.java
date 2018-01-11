package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.TableDetails;

import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class KDMainAdapter extends RecyclerView.Adapter<KDMainAdapter.ViewHolder> {

    private Context mContext;
    private List<TableDetails> data;


    public KDMainAdapter(Context context,List<TableDetails> data) {
        this.mContext = context;
        this.data=data;


    }


    @Override
    public KDMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.kd_main_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final KDMainAdapter.ViewHolder holder, final int position) {

        holder.table_no.setText(""+data.get(position).getTableid());

    }


    @Override
    public int getItemCount() {

        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView table_no;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            table_no = (TextView) itemView.findViewById(R.id.act_kd_main_rv_table_tv);

        }
    }

}