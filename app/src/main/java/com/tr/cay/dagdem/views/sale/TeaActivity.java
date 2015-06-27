package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.util.Log;
import android.widget.TextView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.TeaAdapter;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.model.Product;
import com.tr.cay.dagdem.model.Tea;
import com.tr.cay.dagdem.wrapper.TeaAdapterWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class TeaActivity extends Activity {

    final List<Tea> teaList = new ArrayList<Tea>();
    private Button loginButton;
    private Context context;
    private ListView teaListView;
    private TeaAdapter teaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);

        teaList.add(new Tea("Sarı Paket Dağdem Çay"));
        teaList.add(new Tea("Siyah Paket Dağdem Çay"));
        teaList.add(new Tea("Bostan Çay"));
        teaList.add(new Tea("İki Çay"));

        teaListView = (ListView) findViewById(R.id.teaList);

        teaAdapter = new TeaAdapter(this, teaList);

        teaListView.setAdapter(teaAdapter);

        Customer selectedCustomer = (Customer) getIntent().getSerializableExtra("selectedCustomer");

        context = this.getApplicationContext();

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, OraletTeaActivity.class);
                myIntent.putExtra("selectedTeaList", new TeaAdapterWrapper(teaList));
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*
		final String url = "http://localhost:8080/sale";
        RestTemplate restTemplate = new RestTemplate();
        Integer integer = restTemplate.postForObject(url, sale, Integer.class);
        System.out.println("saleResponseEntity" + integer);
*/
    private class HttpRequestTask extends AsyncTask<Void, Void, List<Product>>
    {
        @Override
        protected  List<Product> doInBackground(Void... params) {
            List<Product> productList = new ArrayList<Product>();
            try {
                final String url = "http://10.0.2.2:8080/products?productType=1";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Object productObjects[] = restTemplate.getForObject(url, Object[].class);
                for(Object object:productObjects)
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
						    product.setQuantity((Integer)entry.getValue());
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
            teaList.clear();
            for(Product product:productList)
            {
                teaList.add(new Tea(product.getProductName()));
            }
            teaAdapter.notifyDataSetChanged();
        }

    }
}

