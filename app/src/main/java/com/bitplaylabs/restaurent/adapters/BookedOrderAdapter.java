package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.BookedDetailModel;

import java.util.List;

/**
 * Created by vivek yadav on 26-10-2017.
 */

public class BookedOrderAdapter extends RecyclerView.Adapter<BookedOrderAdapter.ViewHolder> {

    private Context mContext;

    public List<BookedDetailModel> itemslist;

  //  public List<UpdateItems> updateItemsList;


    public BookedOrderAdapter(Context context/*, List<BookedDetailModel> itemslist*/) {
        this.mContext = context;
       /* this.itemslist = itemslist;*/
        // this.name=name;
      //  updateItemsList = new ArrayList<UpdateItems>();



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

      //  holder.item_Name.setText(""+itemslist.size());
    /*    holder.item_Name.setText(""+itemslist.get(position).bookedItemName);
        holder.quality.setText(""+itemslist.get(position).getBookedItemQuantity());
        holder.count.setText(String.valueOf(position + 1) + ".");*/

      /*  holder.editMoreIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.quality.setFocusable(true);
                holder.quality.setEnabled(true);
                holder.quality.setCursorVisible(true);
                holder.editDoneIv.setVisibility(View.VISIBLE);
                holder.editMoreIV.setVisibility(View.GONE);

            }
        });
*/

    }

    @Override
    public int getItemCount() {
      //  Log.d("BOA",""+itemslist.size());

        return /*itemslist.size()*/10;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_Name, count;
        public EditText quality;
        public ImageView editMoreIV,editDoneIv;

        public ViewHolder(final View itemView) {
            super(itemView);

          //  itemView.setClickable(true);
            item_Name = (TextView) itemView.findViewById(R.id.namee);
            quality = (EditText) itemView.findViewById(R.id.quality);
            count = (TextView) itemView.findViewById(R.id.item_count_tv);
            editMoreIV = (ImageView) itemView.findViewById(R.id.item_setting_iv);
            editDoneIv=(ImageView)itemView.findViewById(R.id.item_setting_done_iv);
           /* itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    //    int position = getLayoutPosition();

                    delete(getAdapterPosition());
                    Toast.makeText(mContext, "" + item_Name.getText().toString() + " Delected", Toast.LENGTH_SHORT).show();
                    return true;
                }

                private void delete(int position) {

                    itemslist.remove(position);
                    notifyItemRemoved(position);
                }
            });*/

        }
    }

}