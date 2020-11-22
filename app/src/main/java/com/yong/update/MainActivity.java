package com.yong.update;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String versionName, packageName = "com.yong.sbus";;
    int versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder( ).permitAll( ).build( );
        StrictMode.setThreadPolicy(policy);

        TextView textview = (TextView)findViewById(R.id.text);

        try {
            PackageInfo pInfo = getPackageManager( ).getPackageInfo(getPackageName( ), PackageManager.GET_META_DATA);

            versionName = pInfo.versionName;
            versionCode = pInfo.versionCode;
            Log.e("", "device_version : " + pInfo.versionName + "   " + pInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace( );
        }
        textview.setText("버전 이름 : " + versionName + " 버전 코드 : " + versionCode);


        textview.setText(getMarketVersionFast(packageName));

    }

    public static String getMarketVersionFast(String packageName) {

        String mData = "", mVer = null;

        try {
            //URL mUrl = new URL("https://play.google.com/store/apps/details?id=" + packageName);
            URL mUrl = new URL("https://play.google.com/store/apps/details?id=com.yong.sbus");


            HttpURLConnection mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) {
                return "mConnection == null....";
            }

            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);

            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                while(true) {
                    String line = bufferedReader.readLine();
                    if (line == null)
                        break;
                    mData += line;
                }
                bufferedReader.close();
            }
            mConnection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Exception ex";
        }

        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = mData.indexOf(startToken);

        if (index == -1) {
            mVer = null;
        } else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }
        //return mVer;
        return "성공......" + mData;
    }
}