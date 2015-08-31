package com.tr.cay.dagdem.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Product;

import java.util.List;

/**
 * Created by EXT0175855 on 5/15/2015.
 */
public class SummaryTeaAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Product> productList;
    private Activity activity;

    public SummaryTeaAdapter(Activity activity, List<Product> productList)
    {
        this.activity = activity;
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        this.productList = productList;
    }

    @Override
    public int getCount()
    {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        //şöyle de olabilir: public Object getItem(int position)
        return productList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = mInflater.inflate(R.layout.summary_satir_layout, parent,false);
        final Product product = productList.get(position);

        TextView productNameView = (TextView) rowView.findViewById(R.id.productName);
        productNameView.setText(product.getProductName());

        TextView productQuantityTextView = (TextView) rowView.findViewById(R.id.productQuantity);
        productQuantityTextView.setText(String.valueOf(product.getSaleQuantity()));

        Button deleteProductButton = (Button) rowView.findViewById(R.id.deleteProductButton);
        deleteProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                productList.remove(product);
                SummaryTeaAdapter.this.notifyDataSetChanged();
            }
        });


        EditText productSalePriceText = (EditText) rowView.findViewById(R.id.productSalePrice);
        productSalePriceText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0)
            {
                if(arg0.length()>0)
                {
                    product.setSalePrice(Double.parseDouble(arg0.toString()));
                }
            }

        });
      return rowView;
    }



}
