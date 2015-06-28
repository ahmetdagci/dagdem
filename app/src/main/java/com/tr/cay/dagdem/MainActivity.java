package com.tr.cay.dagdem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tr.cay.dagdem.adapter.CustomerSingleListAdapter;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.views.sale.TeaActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity
{
    final List<Customer> customerList = new ArrayList<Customer>();
    final List<Customer> searchCustomerList = new ArrayList<Customer>();
    private Button selectCustomerButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerList.add(new Customer("123","Ahmet Yılmaz"));
        customerList.add(new Customer("124","Ayşe Küçük"));
        customerList.add(new Customer("125","Fatma Bulgurcu"));
        customerList.add(new Customer("126","İzzet Altınmeşe"));
        customerList.add(new Customer("127","Melek Subaşı"));
        customerList.add(new Customer("128","Selim Serdilli"));
        customerList.add(new Customer("129","Halil İbrahim"));
        customerList.add(new Customer("130","Serap Okumuş"));

        searchCustomerList.addAll(customerList);

        final ListView customerListView = (ListView) findViewById(R.id.customerList);


        final CustomerSingleListAdapter customerAdapter = new CustomerSingleListAdapter(this, searchCustomerList);

        final EditText searchBox = (EditText) findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher(){
             public void onTextChanged(CharSequence s, int start, int before, int count)
             {
                 String searchString = searchBox.getText().toString();
                 int textLength = searchString.length();
                 searchCustomerList.clear();

                 for (int i = 0; i < customerList.size(); i++)
                 {
                     String customerName = customerList.get(i).getName();
                     if (textLength <= customerName.length())
                     {
                         if (customerName.contains(searchString))
                             searchCustomerList.add(customerList.get(i));
                     }
                 }
                 customerAdapter.notifyDataSetChanged();
             }

             public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

             public void afterTextChanged(Editable s) { }
         }
        );
        searchBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    hideKeyboard(v);
                }
            }
        });

        customerListView.setAdapter(customerAdapter);

        context = this.getApplicationContext();

        selectCustomerButton = (Button) findViewById(R.id.selectCustomerButton);

        selectCustomerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, TeaActivity.class);
                Customer selectedCustomer = Customer.findSelectedCustomer(searchCustomerList);
                myIntent.putExtra("selectedCustomer", selectedCustomer);
                startActivity(myIntent);
            }
        });

    }

    private void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
