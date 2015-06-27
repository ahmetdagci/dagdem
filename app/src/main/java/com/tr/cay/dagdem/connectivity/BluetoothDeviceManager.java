package com.tr.cay.dagdem.connectivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by EXT0175855 on 6/9/2015.
 */
public class BluetoothDeviceManager {

    private BluetoothAdapter mBluetoothAdapter = null;

    private static final int REQUEST_ENABLE_BT = 3;

    public void init(Activity activity)
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(activity, "Bluetooth desteklenmiyor", Toast.LENGTH_LONG).show();
            activity.finish();
        }
        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

}
