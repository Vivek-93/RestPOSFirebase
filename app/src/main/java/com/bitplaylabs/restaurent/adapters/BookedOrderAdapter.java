package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.BookedDetailModel;
import com.bitplaylabs.restaurent.extra.SearchItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class BookedOrderAdapter extends RecyclerView.Adapter<BookedOrderAdapter.ViewHolder> {

    private Context mContext;

    public List<SearchItemModel> itemslist;
    public List<SearchItemModel> upDatelist;
    private int pos;
    private final BookedActivityonClick mClick;

    public interface BookedActivityonClick {
        void onClicked(List<SearchItemModel> data, int position);

    }

    public BookedOrderAdapter(Context context, List<SearchItemModel> itemslist, BookedActivityonClick mClick) {
        this.mContext = context;
        this.itemslist = itemslist;
        this.mClick = mClick;
        upDatelist = new ArrayList<>();

    }

    @Override
    public BookedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booked_recyclerview_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BookedOrderAdapter.ViewHolder holder, final int position) {

        holder.item_Name.setText("" + itemslist.get(position).getSearchItem());
        holder.quality.setText("" + itemslist.get(position).getItemQuantity());
        holder.count.setText(String.valueOf(position + 1) + ".");



        upDatelist = itemslist;

        upDatelist = updateOrderList(position,Integer.parseInt(holder.quality.getText().toString()), upDatelist);

        mClick.onClicked(upDatelist, position);


    }

    private List<SearchItemModel> updateOrderList(int position, int newValue, List<SearchItemModel> mList) {
        SearchItemModel searchItemModel = mList.get(position);
        searchItemModel.setItemQuantity(newValue);
        mList.set(position, searchItemModel);
        return mList;
    }


    @Override
    public int getItemCount() {
        return itemslist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_Name, count;
        public EditText quality;

        public ViewHolder(final View itemView) {
            super(itemView);

            item_Name = (TextView) itemView.findViewById(R.id.namee);
            quality = (EditText) itemView.findViewById(R.id.quality);
            count = (TextView) itemView.findViewById(R.id.item_count_tv);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int position = getLayoutPosition();
                    delete(getAdapterPosition());
                    Toast.makeText(mContext, "" + item_Name.getText().toString() + " Delected", Toast.LENGTH_SHORT).show();
                    return true;
                }

                private void delete(int position) {

                    itemslist.remove(position);
                    notifyItemRemoved(position);
                }
            });

        }
    }

}