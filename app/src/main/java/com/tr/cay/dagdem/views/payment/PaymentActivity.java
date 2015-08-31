package com.tr.cay.dagdem.views.payment;

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
import android.widget.EditText;
import android.widget.TextView;

import com.tr.cay.dagdem.MainActivity;
import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.model.Product;
import com.tr.cay.dagdem.util.AlertDialogUtil;
import com.tr.cay.dagdem.views.AbstractActivity;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AbstractActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        context = this.getApplicationContext();
        getActionBar().hide();

        final Customer selectedCustomer = (Customer) getIntent().getSerializableExtra("selectedCustomer");
        TextView selectedCustomerTextViewId = (TextView) findViewById(R.id.selectedCustomerTextViewId);
        selectedCustomerTextViewId.setText(selectedCustomer.getNameLastName());

        Button customerPaymentButton = (Button) findViewById(R.id.customerPaymentButton);

        customerPaymentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText payment = (EditText)findViewById(R.id.customerPaymentText);
                new HttpRequestTask(selectedCustomer,new BigDecimal(payment.getText().toString())).execute();
            }
        });

        Button returnToCustomerPage = (Button)findViewById(R.id.returnToCustomerPage);
        returnToCustomerPage.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent myIntent = new Intent(context, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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

    private class HttpRequestTask extends AsyncTask<Void, Void, Integer>
    {
        private Customer customer;
        private BigDecimal paymentAmount;

        public HttpRequestTask(Customer customer,BigDecimal paymentAmount)
        {
            this.customer = customer;
            this.paymentAmount = paymentAmount;
        }
        @Override
        protected  Integer doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:3131/dagdem-ws/saveRevenue?userCode=resul&paymentAmount="+paymentAmount;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Integer> integerResponseEntity = restTemplate.postForEntity(url, customer, Integer.class);
                return integerResponseEntity.getBody();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer paymentResult)
        {
            if(paymentResult==0)
            {
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(PaymentActivity.this);
                alertDialogUtil.showMessage("","Tahsilat işlemi başarıyla yapıldı");
            }else
            {
                AlertDialogUtil alertDialogUtil = new AlertDialogUtil(PaymentActivity.this);
                alertDialogUtil.showMessage("Uyarı","Tahsilat işlemi yapılamadı");
            }
        }

    }
}
