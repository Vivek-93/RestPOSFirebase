package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.MenuList;
import com.bitplaylabs.restaurent.extra.SearchItemModel;

import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class CaptainSearchAdapter extends RecyclerView.Adapter<CaptainSearchAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchItemModel> mSearch;


    public CaptainSearchAdapter(Context context, List<SearchItemModel> search) {
        this.mContext = context;
        this.mSearch = search;

    }


    @Override
    public CaptainSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.captain_search_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CaptainSearchAdapter.ViewHolder holder, final int position) {

        holder.mSerailNo.setText("" + (position + 1) + ".");
        holder.mSearchItemName.setText(mSearch.get(position).getSearchItem().toString());
        holder.mQuantity.setText(""+mSearch.get(position).getItemQuantity());

     /*   Log.d("CaptionSearchAdapter","quantity"+mSearch.get(position).getQuantity());
        holder.mQuantity.setText(""+mSearch.get(position).getQuantity());
*/
    }


    @Override
    public int getItemCount() {
        Log.d("CSA",""+mSearch.size());
        return mSearch.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mSearchItemName, mSerailNo ,mQuantity;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            mSearchItemName = (TextView) itemView.findViewById(R.id.fragment_caption_search_item_tv);
            mSerailNo = (TextView) itemView.findViewById(R.id.fragment_caption_search_serial_tv);
            mQuantity=(TextView)itemView.findViewById(R.id.fragment_caption_search_item_quantity_tv);

        }
    }

}