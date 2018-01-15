package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.MenuList;

import java.util.List;

/**
 * Created by anees on 11-01-2018.
 */

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    private List<MenuList> subData;
    private Context mContext;
    private int pos;
    private String mCatogery;
    private final SubCatogeryonClick mClick;
    private int row_index = -1;

    public interface SubCatogeryonClick {
        void onClicked(MenuList data, int pos);

    }


    public SubCategoryAdapter(Context context, int pos, List<MenuList> subData, SubCatogeryonClick mClick) {
        this.mContext = context;
        this.pos = pos;
        this.subData = subData;
        this.mClick = mClick;
    }


    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_recyclerview_sub_category_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClick.onClicked(subData.get(position), position);
                row_index = position;
                notifyDataSetChanged();

            }
        });
        if (row_index == position) {
            holder.mCardView.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        } else {
            holder.mCardView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }

        holder.mItemName.setText(subData.get(position).getSubcategory().toString());

    }

    @Override
    public int getItemCount() {
        return subData.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemName;
        public CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            mItemName = (TextView) itemView.findViewById(R.id.caption_sub_sub_item_tv);
            mCardView = (CardView) itemView.findViewById(R.id.caption_card_view_sub_sub_item);

        }
    }
}
