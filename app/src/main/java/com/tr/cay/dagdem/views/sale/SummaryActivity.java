package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.SummaryTeaAdapter;
import com.tr.cay.dagdem.model.Tea;
import com.tr.cay.dagdem.wrapper.TeaAdapterWrapper;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        final ListView teaListView = (ListView) findViewById(R.id.summarySaleList);
        Button calculateProductPriceButton = (Button)findViewById(R.id.calculateProductPricesButton);
        calculateProductPriceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        SummaryTeaAdapter summaryTeaAdapter = new SummaryTeaAdapter(this, filterSelectedTea());
        teaListView.setAdapter(summaryTeaAdapter);
    }

    private List<Tea> filterSelectedTea()
    {
        List<Tea> teaList = new ArrayList<Tea>();
        TeaAdapterWrapper selectedTeaListAdapterWrapper = (TeaAdapterWrapper) getIntent().getSerializableExtra("selectedTeaList");
        for(Tea tea : selectedTeaListAdapterWrapper.getTeaList())
        {
            if (tea.isChecked())
            {
                teaList.add(tea);
            }
        }
        return teaList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
