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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Product;

import java.util.List;

/**
 * Created by EXT0175855 on 8/16/2015.
 */
public class ProductAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Product> productList;

    public ProductAdapter(Activity activity, List<Product> productList)
    {
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
    public Product getItem(int position)
    {
        //şöyle de olabilir: public Object getItem(int position)
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final View rowView = mInflater.inflate(R.layout.product_satir_layout, null);

        final Product product = productList.get(position);

        TextView productName = (TextView) rowView.findViewById(R.id.isimsoyisim);
        productName.setText(product.getProductName());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.userSelected);
        checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                product.setChecked(cb.isChecked());
                if(!cb.isChecked())
                {
                    product.setSaleQuantity(0);
                    EditText productCountText = (EditText) rowView.findViewById(R.id.productCount);
                    productCountText.setText(""+product.getSaleQuantity());
                }
            }
        });

        Button increaseCountButton = (Button) rowView.findViewById(R.id.increaseProductCountButton);
        increaseCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                product.setSaleQuantity(product.getSaleQuantity()+1);
                EditText productCountText = (EditText) rowView.findViewById(R.id.productCount);
                productCountText.setText(""+product.getSaleQuantity());
                CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.userSelected);
                checkBox.setChecked(true);
            }
        });

        Button decreaseCountButton = (Button) rowView.findViewById(R.id.decreaseProductCountButton);
        decreaseCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(product.getSaleQuantity()>0)
                {
                    product.setSaleQuantity(product.getSaleQuantity()-1);
                    EditText productCountText = (EditText) rowView.findViewById(R.id.productCount);
                    productCountText.setText(""+product.getSaleQuantity());
                }
                if(product.getSaleQuantity()==0)
                {
                    CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.userSelected);
                    checkBox.setChecked(false);
                }
            }
        });

        EditText productCountText = (EditText) rowView.findViewById(R.id.productCount);
        productCountText.addTextChangedListener(new TextWatcher()
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
                product.setSaleQuantity(Integer.parseInt(arg0.toString()));
            }
        });
        return rowView;
    }
}
