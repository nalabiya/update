package com.yong.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String versionName;
    int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView textview = (TextView)findViewById(R.id.text);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);

            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
            Log.e("", "device_version : " + pInfo.versionName + "   " + pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        textview.setText("버전 이름 : " + versionName + " 버전 코드 : " + versionCode);
    }
}