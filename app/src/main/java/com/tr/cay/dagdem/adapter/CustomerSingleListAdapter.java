package com.tr.cay.dagdem.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.views.customLayout.InertCheckBox;

import java.util.List;

/**
 * Created by EXT0175855 on 5/18/2015.
 */
public class CustomerSingleListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Customer> customerList;

    public CustomerSingleListAdapter(Activity activity, List<Customer> customerList)
    {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        this.customerList = customerList;
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
        if (rowView == null)
        {
            rowView = mInflater.inflate(R.layout.single_choice_items, parent, false);
        }

        final Customer customer = customerList.get(position);

        TextView textView = (TextView) rowView.findViewById(R.id.singleItemId);
        textView.setText(customer.getName());

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


    private void uncheckedAllCustomerList()
    {
        for(Customer customer:customerList)
        {
            customer.setChecked(false);
        }
    }
}
