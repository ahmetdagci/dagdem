package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.ProductAdapter;
import com.tr.cay.dagdem.enums.ProductType;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.model.Product;
import com.tr.cay.dagdem.views.AbstractActivity;
import com.tr.cay.dagdem.wrapper.ProductAdapterWrapper;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OraletTeaActivity extends AbstractActivity {

    final List<Product> oraletProductTeaList = new ArrayList<Product>();
    private Context context;

    private ProductAdapter oraletProductTeaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oralet_tea);
        getActionBar().hide();
        new HttpRequestTask().execute();

        final ListView oraletTeaListView = (ListView) findViewById(R.id.oraletTeaList);

        oraletProductTeaAdapter = new ProductAdapter(this, oraletProductTeaList);

        oraletTeaListView.setAdapter(oraletProductTeaAdapter);

        context = this.getApplicationContext();

        Button oraletTeaNextButton = (Button) findViewById(R.id.oraletTeaNextButton);

        oraletTeaNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, SummaryActivity.class);
                ProductAdapterWrapper selectedTeaProductListAdapterWrapper = (ProductAdapterWrapper) getIntent().getSerializableExtra("selectedTeaList");
                Customer selectedCustomer = (Customer) getIntent().getSerializableExtra("selectedCustomer");
                myIntent.putExtra("selectedOraletTeaList", new ProductAdapterWrapper(oraletProductTeaList));
                myIntent.putExtra("selectedTeaList", selectedTeaProductListAdapterWrapper);
                myIntent.putExtra("selectedCustomer", selectedCustomer);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    private class HttpRequestTask extends AsyncTask<Void, Void, List<Product>>
    {
        @Override
        protected  List<Product> doInBackground(Void... params) {
            List<Product> productList = new ArrayList<Product>();
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Object[]> responseEntity = restTemplate.postForEntity("http://10.0.2.2:3131/dagdem-ws/products", ProductType.ORALET, Object[].class);
                for(Object object:responseEntity.getBody())
                {
                    Product product = new Product();
                    LinkedHashMap linkedHashMap = (LinkedHashMap)object;
                    Set set = linkedHashMap.entrySet();
                    Iterator iterator = set.iterator();
                    while(iterator.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        if(entry.getKey().equals("id"))
                        {
                            product.setId(entry.getValue().toString());
                        }
                        else if(entry.getKey().equals("productName"))
                        {
                            product.setProductName(entry.getValue().toString());
                        }
                        else if(entry.getKey().equals("price"))
                        {
                            product.setPrice((Double)entry.getValue());
                        }
                        else if(entry.getKey().equals("quantity"))
                        {
                            product.setStockQuantity((Integer)entry.getValue());
                        }
                    }
                    productList.add(product);
                }
                return productList;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Product> productList)
        {
            oraletProductTeaList.clear();
            oraletProductTeaList.addAll(productList);
            oraletProductTeaAdapter.notifyDataSetChanged();
        }

    }
}
