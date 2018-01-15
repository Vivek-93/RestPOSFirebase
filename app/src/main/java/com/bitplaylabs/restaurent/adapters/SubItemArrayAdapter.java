package com.bitplaylabs.restaurent.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.MenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class SubItemArrayAdapter extends RecyclerView.Adapter<SubItemArrayAdapter.ViewHolder> {

    private List<MenuList> data;
    private Context mContext;
    private String mCatogery;
    private Spinner itemQuantitySpinner;
    private Dialog additemsDialogBox;
    private List<String> addQuantity;

    private final AddCartButtonClick mClick;

    public interface AddCartButtonClick {

        void onClicked(String itemname, int quantity, float price);
    }


    public SubItemArrayAdapter(Context context, List<MenuList> data, AddCartButtonClick mClick) {
        this.mContext = context;
        this.data = data;
        this.mClick = mClick;
    }


    @Override
    public SubItemArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.table_recyclerview_sub_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubItemArrayAdapter.ViewHolder holder, final int position) {


        holder.sub_item_Name.setText(data.get(position).getItemname().toString());
        if ((data.get(position).getMealtype().toString().equalsIgnoreCase("Veg"))) {
            holder.mGreenDot.setVisibility(View.VISIBLE);
            holder.mRedDot.setVisibility(View.GONE);
        } else {
            holder.mGreenDot.setVisibility(View.GONE);
            holder.mRedDot.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    additemsDialogBox = new Dialog(mContext);
                    additemsDialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    additemsDialogBox.setContentView(R.layout.item_table_detail_item_quantity_bill);
                    additemsDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    additemsDialogBox.getWindow().setGravity(Gravity.CENTER);
                    additemsDialogBox.show();
                    TextView itemQuality = (TextView) additemsDialogBox.findViewById(R.id.item_table_details_quantity_tv);
                    Button addBucket = (Button) additemsDialogBox.findViewById(R.id.item_table_details_quantity_button);
                    itemQuantitySpinner = (Spinner) additemsDialogBox.findViewById(R.id.item_table_details_quantity_spinner);
                    itemQuality.setText((data.get(position).getItemname().toString()).replace("\"", ""));

                    addQuantity = new ArrayList<String>();
                    addQuantity.add("" + 1);
                    addQuantity.add("" + 2);
                    addQuantity.add("" + 3);
                    addQuantity.add("" + 4);
                    addQuantity.add("" + 5);
                    addQuantity.add("" + 6);
                    addQuantity.add("" + 7);
                    addQuantity.add("" + 8);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, addQuantity);
                    itemQuantitySpinner.setAdapter(adapter);

                    addBucket.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int quantity = Integer.parseInt(itemQuantitySpinner.getSelectedItem().toString());
                            String itemName = data.get(position).getItemname().toString();
                            String price = data.get(position).getPrice().toString();
                            mClick.onClicked(itemName,quantity, Float.parseFloat(price));
                            additemsDialogBox.dismiss();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sub_item_Name;
        public CardView mCardView;
        public ImageView mGreenDot, mRedDot;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            sub_item_Name = (TextView) itemView.findViewById(R.id.table_recyclerview_sub_item_tv);
            mCardView = (CardView) itemView.findViewById(R.id.caption_card_view_sub_item);
            mGreenDot = (ImageView) itemView.findViewById(R.id.table_recyclerview_sub_item_green_iv);
            mRedDot = (ImageView) itemView.findViewById(R.id.table_recyclerview_sub_item_red_iv);


        }
    }

}