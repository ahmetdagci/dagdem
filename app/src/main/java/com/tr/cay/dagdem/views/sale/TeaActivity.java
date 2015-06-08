package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.TeaAdapter;
import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.model.Tea;
import com.tr.cay.dagdem.wrapper.TeaAdapterWrapper;

import java.util.ArrayList;
import java.util.List;

public class TeaActivity extends Activity {

    final List<Tea> teaList = new ArrayList<Tea>();
    private Button loginButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);

        teaList.add(new Tea("Sarı Paket Dağdem Çay"));
        teaList.add(new Tea("Siyah Paket Dağdem Çay"));
        teaList.add(new Tea("Bostan Çay"));
        teaList.add(new Tea("İki Çay"));

        final ListView teaListView = (ListView) findViewById(R.id.teaList);

        final TeaAdapter teaAdapter = new TeaAdapter(this, teaList);

        teaListView.setAdapter(teaAdapter);

        Customer selectedCustomer = (Customer)getIntent().getSerializableExtra("selectedCustomer");

        context = this.getApplicationContext();

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, OraletTeaActivity.class);
                myIntent.putExtra("selectedTeaList", new TeaAdapterWrapper(teaList));
                startActivity(myIntent);
            }
        });

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
}

