package com.tr.cay.dagdem.views.sale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.tr.cay.dagdem.R;
import com.tr.cay.dagdem.adapter.SummaryTeaAdapter;
import com.tr.cay.dagdem.model.Product;
import com.tr.cay.dagdem.util.AlertDialogUtil;
import com.tr.cay.dagdem.wrapper.ProductAdapterWrapper;

import java.util.ArrayList;
import java.util.List;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class SummaryActivity extends Activity
{
    private TextView myLabel;
    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        getActionBar().hide();
        final ListView teaListView = (ListView) findViewById(R.id.summarySaleList);

        final List<Product> selectedProducts = filterSelectedProducts();

        Button calculateProductPriceButton = (Button)findViewById(R.id.calculateProductPricesButton);
        calculateProductPriceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    checkProductPricesFilled(selectedProducts);
                    Double totalAmount = calculateSaleAmount(selectedProducts);
                    TextView totalAccountTextView = (TextView)findViewById(R.id.totalAccountTextView);
                    totalAccountTextView.setText(totalAmount.toString());
                    Button saleButton = (Button) findViewById(R.id.saleButton);
                    saleButton.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                } catch (Exception exception) {
                    AlertDialogUtil alertDialogUtil = new AlertDialogUtil(SummaryActivity.this);
                    alertDialogUtil.showMessage("Uyarı",exception.getMessage());
                }
            }
        });

        Button saleButton = (Button)findViewById(R.id.saleButton);
        saleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Button printProductPricesButton = (Button)findViewById(R.id.printProductPricesButton);
                printProductPricesButton.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
            }
        });

        Button printProductPricesButton = (Button)findViewById(R.id.printProductPricesButton);
        printProductPricesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    findBT();
                    openBT();
                } catch (IOException ex) {
                }
            }
        });

        SummaryTeaAdapter summaryTeaAdapter = new SummaryTeaAdapter(this, selectedProducts);
        teaListView.setAdapter(summaryTeaAdapter);
    }

    private List<Product> filterSelectedProducts()
    {
        List<Product> productList = new ArrayList<Product>();
        productList.addAll(filterSelectedProducts((ProductAdapterWrapper) getIntent().getSerializableExtra("selectedTeaList")));
        productList.addAll(filterSelectedProducts((ProductAdapterWrapper) getIntent().getSerializableExtra("selectedOraletTeaList")));
        return productList;
    }

    private List<Product> filterSelectedProducts(ProductAdapterWrapper productAdapterWrapper)
    {
        List<Product> productList = new ArrayList<Product>();
        for(Product product : productAdapterWrapper.getProductList())
        {
            if (product.isChecked())
            {
                productList.add(product);
            }
        }
        return productList;
    }

    private void checkProductPricesFilled(List<Product> selectedProducts) throws Exception {
        for(Product product:selectedProducts)
        {
            if(product.getSalePrice()<1)
            {
                throw new Exception("Lütfen ürünler için fiyat giriniz");
            }
        }
    }

    private Double calculateSaleAmount(List<Product> selectedProducts)
    {
        Double totalAmount = 0d;
        for(Product product:selectedProducts)
        {
            totalAmount = totalAmount+product.getSalePrice()*product.getQuantity();
        }
        return totalAmount;
    }

    // This will find a bluetooth printer device
    private void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if (!mBluetoothAdapter.isEnabled())
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                    .getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    // MP300 is the name of the bluetooth printer device
                    if (device.getName().equals("MP300")) {
                        mmDevice = device;
                        break;
                    }
                }
            }
            myLabel.setText("Bluetooth Yazıcı bulundu");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tries to open a connection to the bluetooth printer device
    private void openBT() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Yazıcıyla bağlantı kuruldu");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,encodedBytes, 0,encodedBytes.length);
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();
        } catch (NullPointerException e) {
        } catch (Exception e) {
        }
    }

    /*
     * This will send data to be printed by the bluetooth printer
     */
    private void sendData() throws IOException {
        try {

            // the text typed by the user
            String msg = "test";
            msg += "\n";

            mmOutputStream.write(msg.getBytes());

            // tell the user data were sent
            myLabel.setText("Yazdırılıyor");

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Close the connection to bluetooth printer.
    private void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Yazıcıyla bağlantı kapatıldı");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
