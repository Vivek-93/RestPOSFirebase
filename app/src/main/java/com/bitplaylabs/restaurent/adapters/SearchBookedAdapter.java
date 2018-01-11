package com.bitplaylabs.restaurent.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.bitplaylabs.restaurent.R;
import com.bitplaylabs.restaurent.extra.SearchBookedList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivek on 11-01-2018.
 */

public class SearchBookedAdapter extends ArrayAdapter<SearchBookedList> {

    Context context;
    int resource, textViewResourceId;
    List<SearchBookedList> items, tempItems, suggestions;

    public SearchBookedAdapter(@NonNull Context context ,List<SearchBookedList> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.items = objects ;
        this.tempItems =new ArrayList<SearchBookedList>(objects);
        this.suggestions =new ArrayList<SearchBookedList>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchBookedList items = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.items_row, parent, false);
        }

        TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvCustomer);


        if (txtCustomer != null)
            txtCustomer.setText(items.getItemName() + " " + items.getItemPrice());


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            SearchBookedList customer = (SearchBookedList) resultValue;
            return customer.getItemName() + " " + customer.getItemPrice();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SearchBookedList people : tempItems) {
                    if (people.getItemName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<SearchBookedList> c = (ArrayList<SearchBookedList>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SearchBookedList cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
            else{
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
