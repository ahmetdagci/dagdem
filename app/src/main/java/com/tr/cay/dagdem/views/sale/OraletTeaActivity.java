package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.OraletTeaAdapter;
import com.tr.cay.dagdem.model.OraletTea;
import com.tr.cay.dagdem.wrapper.OraletTeaAdapterWrapper;
import com.tr.cay.dagdem.wrapper.TeaAdapterWrapper;

import java.util.ArrayList;
import java.util.List;

public class OraletTeaActivity extends Activity {

    final List<OraletTea> oraletTeaList = new ArrayList<OraletTea>();
    private Button oraletTeaNextButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oralet_tea);
        getActionBar().hide();
        oraletTeaList.add(new OraletTea("Limonlu"));
        oraletTeaList.add(new OraletTea("Elmalı"));
        oraletTeaList.add(new OraletTea("Böğürtlenli"));
        oraletTeaList.add(new OraletTea("Ahududu"));

        final ListView oraletTeaListView = (ListView) findViewById(R.id.oraletTeaList);

        final OraletTeaAdapter oraletTeaAdapter = new OraletTeaAdapter(this, oraletTeaList);

        oraletTeaListView.setAdapter(oraletTeaAdapter);

        context = this.getApplicationContext();

        oraletTeaNextButton = (Button) findViewById(R.id.oraletTeaNextButton);

        oraletTeaNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent myIntent = new Intent(context, SummaryActivity.class);
                TeaAdapterWrapper selectedTeaListAdapterWrapper = (TeaAdapterWrapper) getIntent().getSerializableExtra("selectedTeaList");
                myIntent.putExtra("selectedOraletTeaList", new OraletTeaAdapterWrapper(oraletTeaList));
                myIntent.putExtra("selectedTeaList", selectedTeaListAdapterWrapper);
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
