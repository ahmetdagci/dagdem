package com.tr.cay.dagdem.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Tea;

import java.util.List;

/**
 * Created by EXT0175855 on 5/13/2015.
 */
public class TeaAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Tea> mTeaListesi;

    public TeaAdapter(Activity activity, List<Tea> teaList)
    {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mTeaListesi = teaList;
    }

    @Override
    public int getCount()
    {
        return mTeaListesi.size();
    }

    @Override
    public Tea getItem(int position)
    {
        //şöyle de olabilir: public Object getItem(int position)
        return mTeaListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowView = mInflater.inflate(R.layout.satir_layout, null);

        final Tea tea = mTeaListesi.get(position);

        TextView textView = (TextView) rowView.findViewById(R.id.isimsoyisim);
        textView.setText(tea.getIsim());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.userSelected);
        checkBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                tea.setChecked(cb.isChecked());
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
                tea.setAdet(Integer.parseInt(arg0.toString()));
            }
        });
        return rowView;
    }
}
