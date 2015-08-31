package com.tr.cay.dagdem;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tr.cay.dagdem.model.Customer;
import com.tr.cay.dagdem.wsmodel.CustomerTrafficReport;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CustomerLoanFragment extends DialogFragment {
    private static final String ARG_CUSTOMER = "selectedCustomer";

    private Customer selectedCustomer;

    private TextView totalSaleAmountTextView;
    private TextView totalPaymentAmountTextView;
    private TextView totalLoanAmountTextView;

    public CustomerLoanFragment() {
    }

    public static CustomerLoanFragment newInstance(Customer customer) {
        CustomerLoanFragment fragment = new CustomerLoanFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CUSTOMER, customer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog);
        if (getArguments() != null) {
            selectedCustomer = (Customer) getArguments().getSerializable(ARG_CUSTOMER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setTitle("Müşeri Satış Bilgisi Güncelleniyor");
        View customerLoanFragmentView = inflater.inflate(R.layout.fragment_customer_loan, container, false);
        totalSaleAmountTextView = (TextView) customerLoanFragmentView.findViewById(R.id.totalSaleAmountTextView);
        totalPaymentAmountTextView = (TextView) customerLoanFragmentView.findViewById(R.id.totalPaymentAmountTextView);
        totalLoanAmountTextView = (TextView) customerLoanFragmentView.findViewById(R.id.totalLoanAmountTextView);
        return customerLoanFragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(800, 400);
        Customer customer = (Customer) getArguments().getSerializable(ARG_CUSTOMER);
        new HttpRequestTask().execute(customer);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class HttpRequestTask extends AsyncTask<Customer, Void, CustomerTrafficReport> {
        @Override
        protected CustomerTrafficReport doInBackground(Customer... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Map<String, String> wsParams = new HashMap<String, String>();
                wsParams.put("customerId", params[0].getCustomerId());
                CustomerTrafficReport customerTrafficReport = new CustomerTrafficReport();
                Object object = restTemplate.getForObject("http://10.0.2.2:3131/dagdem-ws/queryCustomerLoanAndIncome/{customerId}", Object.class, wsParams);
                LinkedHashMap linkedHashMap = (LinkedHashMap) object;
                Set set = linkedHashMap.entrySet();
                Iterator iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getKey().equals("customerNo")) {
                        customerTrafficReport.setCustomerNo(entry.getValue().toString());
                    } else if (entry.getKey().equals("totalPaymentAmount")) {
                        if (entry.getValue() != null)
                            customerTrafficReport.setTotalPaymentAmount(new BigDecimal(entry.getValue().toString()));
                    } else if (entry.getKey().equals("totalSaleAmount")) {
                        if (entry.getValue() != null)
                            customerTrafficReport.setTotalSaleAmount(new BigDecimal(entry.getValue().toString()));
                    }
                }
                return customerTrafficReport;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(CustomerTrafficReport customerTrafficReport) {
            if (customerTrafficReport != null) {
                getDialog().setTitle("Müşteri Satış Bilgileri");
                totalSaleAmountTextView.setText(customerTrafficReport.getTotalSaleAmount().toString());
                totalPaymentAmountTextView.setText(customerTrafficReport.getTotalPaymentAmount().toString());
                totalLoanAmountTextView.setText(customerTrafficReport.getTotalSaleAmount().subtract(customerTrafficReport.getTotalPaymentAmount()).toString());
            }
        }

    }
}