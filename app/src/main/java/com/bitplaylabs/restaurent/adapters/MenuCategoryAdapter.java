package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.MenuList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.ViewHolder> {

    private List<MenuList> data;
    private Context mContext;
    private final CatogeryonClick mClick;
    private final int pos;
    private int row_index=-1;

    public interface CatogeryonClick {
        void onClicked(MenuList data, int pos);

    }

    public MenuCategoryAdapter(Context mContext, int pos, List<MenuList> cogetaryList, CatogeryonClick mClick) {
        this.mContext=mContext;
        this.pos = pos;
        this.data=cogetaryList;
        this.mClick = mClick;





    }



    @Override
    public MenuCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MenuCategoryAdapter.ViewHolder holder, final int position) {
      //  HashSet hashSet = new HashSet(data);

        holder.item_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClick.onClicked(data.get(position), position);
                row_index=position;
                notifyDataSetChanged();

            }
        });
        if(row_index==position){
            holder.item_card.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.item_Name.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        }
        else
        {
            holder.item_card.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.item_Name.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }
        holder.item_Name.setText(data.get(position).getCategory());

    }


    @Override
    public int getItemCount() {
        Log.d("MenuCategoryAdapter","list size"+data.size());
        return data.size();



    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_Name;
        public LinearLayout item_card;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            item_Name = (TextView) itemView.findViewById(R.id.table_item_meal_name_tv);
            item_card = (LinearLayout) itemView.findViewById(R.id.caption_linear_view_item);

        }
    }

}