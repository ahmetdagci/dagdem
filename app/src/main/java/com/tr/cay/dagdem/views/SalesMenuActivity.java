package com.tr.cay.dagdem.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.views.sale.TeaActivity;

public class SalesMenuActivity extends Activity {

    private Context context;
    private Button salesButton;
    private Button paymentButton;
    private Button loanViewButton;
    private Button salesHistoryViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_menu);

        context = this.getApplicationContext();

        salesButton = (Button) findViewById(R.id.salesButton);
        salesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, TeaActivity.class);
                startActivity(myIntent);
            }
        });
        paymentButton = (Button) findViewById(R.id.paymentButton);
        loanViewButton = (Button) findViewById(R.id.loanViewButton);
        salesHistoryViewButton = (Button) findViewById(R.id.salesHistoryViewButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_menu, menu);
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
}

