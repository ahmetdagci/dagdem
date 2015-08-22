package com.tr.cay.dagdem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends Activity
{
    final List<Customer> customerList = new ArrayList<Customer>();
    private Context context;

    private ListView customerListView;
    private CustomerSingleListAdapter customerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

        customerListView = (ListView) findViewById(R.id.customerList);

        customerAdapter = new CustomerSingleListAdapter(this, customerList);

        final EditText searchBox = (EditText) findViewById(R.id.searchBox);
        searchBox.addTextChangedListener(new TextWatcher(){
             public void onTextChanged(CharSequence s, int start, int before, int count)
             {
                 if (count < before)
                 {
                     customerAdapter.resetData();
                 }

                 customerAdapter.getFilter().filter(s.toString());
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

        Button selectCustomerButton = (Button) findViewById(R.id.selectCustomerButton);

        selectCustomerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, TeaActivity.class);
                Customer selectedCustomer = Customer.findSelectedCustomer(customerList);
                myIntent.putExtra("selectedCustomer", selectedCustomer);
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        new HttpRequestTask().execute();
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

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Customer>>
    {
        @Override
        protected  List<Customer> doInBackground(Void... params)
        {
            List<Customer> customerList = new ArrayList<Customer>();
            try {
                final String url = "http://10.0.2.2:3131/dagdem-ws/customers";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Object customerObjects[] = restTemplate.getForObject(url, Object[].class);
                for(Object object:customerObjects)
                {
                    Customer customer = new Customer();
                    LinkedHashMap linkedHashMap = (LinkedHashMap)object;
                    Set set = linkedHashMap.entrySet();
                    Iterator iterator = set.iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        if(entry.getKey().equals("customerId"))
                        {
                            customer.setCustomerId(entry.getValue().toString());
                        }
                        else if(entry.getKey().equals("name"))
                        {
                            customer.setName(entry.getValue().toString());
                        }
                    }
                    customerList.add(customer);
                }
                return customerList;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Customer> customers)
        {
            customerList.clear();
            for(Customer customer:customers)
            {
                customerList.add(customer);
            }
            customerAdapter.notifyDataSetChanged();
        }

    }
}
