package com.bitplaylabs.restaurent.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.TableDetails;
import com.bitplaylabs.restaurent.utils.Sharedpreferences;
import com.bitplaylabs.restaurent.views.activities.BillPrintActivity;
import com.bitplaylabs.restaurent.views.activities.MainActivity;
import com.bitplaylabs.restaurent.views.activities.TableDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Vivek yadav on 18-10-2017.
 */

public class CashierRecyclerViewAdaptor extends RecyclerView.Adapter<CashierRecyclerViewAdaptor.ViewHolder> {


    private List<TableDetails> data = new ArrayList<>();
    private Context mContext;

    private Dialog printDialogBox, tableInfoDialogBox;


    private EditText guestName, guestPhone, guestTable;
  //  private final ProceedButtonClick mClick;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mStateListener;
    private DatabaseReference mRef;
    private Sharedpreferences mPrefs;


   /* public interface ProceedButtonClick {

        void onClicked(String tablekey, String tableid, String headcount, String guestname, String phoneno, String kot);
    }*/

    public CashierRecyclerViewAdaptor(Context context, List<TableDetails> data/*, ProceedButtonClick mClick*/) {
        this.mContext = context;
        this.data = data;
      //  this.mClick = mClick;
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference();
        mPrefs = Sharedpreferences.getUserDataObj(mContext);

    }

    @Override
    public CashierRecyclerViewAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cashier_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final CashierRecyclerViewAdaptor.ViewHolder holder, final int position) {

        holder.myTextView.setText("" + data.get(position).getTablename().toString());


        if (data.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.itemView.setBackgroundColor(Color.GREEN);
            holder.printBillBtn.setEnabled(true);


        } else if (data.get(position).getStatus().equalsIgnoreCase("0")) {

            holder.printBillBtn.setEnabled(false);
            //  holder.printBillBtn.setBackgroundColor(R.color.color_light_blue);
        }

        holder.printBillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printDialogBox = new Dialog(mContext);
                printDialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                printDialogBox.setContentView(R.layout.item_caption_table_bill);
                printDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                printDialogBox.getWindow().setGravity(Gravity.CENTER);
                printDialogBox.show();

                TextView mCancelTV = (TextView) printDialogBox.findViewById(R.id.edit_dialog_cancel_tv);
                TextView mPrintBillTV = (TextView) printDialogBox.findViewById(R.id.edit_dialog_print_tv);

                mPrintBillTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(mContext, BillPrintActivity.class);
                        intent.putExtra("billingTableKey", data.get(position).getTablekey());
                     //   intent.putExtra("captainID", mPrefs.getUserId() );
                        intent.putExtra("tablename",data.get(position).getTablename() );
                        mContext.startActivity(intent);

                        printDialogBox.dismiss();
                    }
                });

                mCancelTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        printDialogBox.dismiss();
                    }
                });

            }


        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView myTextView;
        public Button printBillBtn;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.cashier_table_info);
          //  cardView = (CardView) itemView.findViewById(R.id.caption_card_view_item);
            printBillBtn = (Button) itemView.findViewById(R.id.cashier_rv_billprint_btn);
        }
    }
}
