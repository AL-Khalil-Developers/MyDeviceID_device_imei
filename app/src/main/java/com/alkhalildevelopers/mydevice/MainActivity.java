package com.alkhalildevelopers.mydevice;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/** This code is provided by AL-Khalil Developers
 * whatsapp: +923045281669
 * email: alkhalildevelopers@gmail.com
 */


public class MainActivity extends AppCompatActivity {
    TextView myDeviceIdTxt;
    Button showImeiBTn;
    String device_id = "can't get IMEI on lower than API-26 ";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDeviceIdTxt = findViewById(R.id.myDeviceID);

        showImeiBTn = findViewById(R.id.showImeiBtn);

        showImeiBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDeviceID();
            }
        });
    }

    public void myDeviceID(){
        if (Build.VERSION.SDK_INT >=26){ //this is runtime Permission Method
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                device_id = tm.getImei();
                myDeviceIdTxt.setVisibility(View.VISIBLE);
                myDeviceIdTxt.setText(device_id);

            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted" , Toast.LENGTH_SHORT).show();
                    myDeviceID();
                }else {
                    Toast.makeText(this, "Permission Denied " , Toast.LENGTH_SHORT).show();
                }

                return;

            }
        }
    }
}
