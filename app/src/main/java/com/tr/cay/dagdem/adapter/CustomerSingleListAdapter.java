package com.tr.cay.dagdem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.views.customLayout.InertCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXT0175855 on 5/18/2015.
 */

public class CustomerSingleListAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater mInflater;
    private List<Customer> customerList;
    private List<Customer> originalCustomerList;

    private Filter customerFilter;

    public CustomerSingleListAdapter(Activity activity, List<Customer> customerList)
    {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        this.customerList = customerList;
        this.originalCustomerList = customerList;
    }

    @Override
    public int getCount()
    {
        return customerList.size();
    }

    @Override
    public Customer getItem(int position)
    {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = convertView;

        CustomerHolder customerHolder = new CustomerHolder();
        if (convertView == null)
        {
            rowView = mInflater.inflate(R.layout.single_choice_items, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.singleItemId);
            customerHolder.customerNameView = textView;
            rowView.setTag(customerHolder);
        }
        else
        {
            customerHolder = (CustomerHolder) rowView.getTag();
        }

        final Customer customer = customerList.get(position);

        customerHolder.customerNameView.setText(customer.getName());

        InertCheckBox radioButton = (InertCheckBox) rowView.findViewById(R.id.singleItemCheckBox);
        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                uncheckedAllCustomerList();
                customer.setChecked(isChecked);
            }
        });
        return rowView;
    }

    public void resetData()
    {
        customerList = originalCustomerList;
    }


    private void uncheckedAllCustomerList()
    {
        for(Customer customer:customerList)
        {
            customer.setChecked(false);
        }
    }

    private static class CustomerHolder
    {
        public TextView customerNameView;
    }

    @Override
    public Filter getFilter()
    {
        if (customerFilter == null)
            customerFilter = new CustomerFilter();

        return customerFilter;
    }

    private class CustomerFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = originalCustomerList;
                results.count = originalCustomerList.size();
            }
            else {
                // We perform filtering operation
                List<Customer> customerArrayList = new ArrayList<Customer>();

                for (Customer p : customerList)
                {
                    if (p.getName().contains(constraint.toString()))
                        customerArrayList.add(p);
                }

                results.values = customerArrayList;
                results.count = customerArrayList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results)
        {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                customerList = (List<Customer>) results.values;
                notifyDataSetChanged();
            }

        }

    }
}
