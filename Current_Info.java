package com.example.dilshad.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Current_Info extends AppCompatActivity implements DownloadForecast.AsyncResponses {


    ListView List_Info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__info);

        List_Info = (ListView)findViewById(R.id.list_info);

        ArrayList<String> Items_Loading = new ArrayList<>();
        Items_Loading.add("Loading...");
        ArrayAdapter<String> Loading_Adapter = new ArrayAdapter<String>(this, R.layout.info_list_view, R.id.Temp_Name, Items_Loading);
        List_Info.setAdapter(Loading_Adapter);

        String data = getIntent().getStringExtra("city");
        DownloadForecast downloadForecast = new DownloadForecast();
        String stringUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+data+"&mode=json&units=metric&cnt=3&appid=6b0d85b3fd266e315d8a4273a495f544";
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            downloadForecast.delegating=this;

            downloadForecast.execute(stringUrl);

        }
        else
        {

            ArrayList<String> Items = new ArrayList<>();
            Items.add("No network connection available.");
            ArrayAdapter<String> Not_Found = new ArrayAdapter<String>(this, R.layout.info_list_view, R.id.Temp_Name, Items );
            List_Info.setAdapter(Not_Found);
        }

    }


    @Override
    public void processFinishing(String[] Day_Temp, String[] Night_Temp, String[] Min,String[] Max, String[] Morning, String[] Evening,String[] Date, String[] Pressure,String[] Humidity,String[] Description)
    {
        int Num_Items = Day_Temp.length;
        int I;

        String data = getIntent().getStringExtra("city");


        ArrayList<String> City_Infos = new ArrayList<>();
        City_Infos.add(data);
        for (I = 0; I < Num_Items; I++)
        {
            City_Infos.add(String.valueOf(I+1) + ". Time : " + Date[I]);
            City_Infos.add("        Morning Temperature(°C) : " + Morning[I]);
            City_Infos.add("        Evening Temperature(°C) : " + Evening[I]);
            City_Infos.add("        Day temperature(°C) : " + Day_Temp[I]);
            City_Infos.add("        Night temperature(°C) : " + Night_Temp[I]);
            City_Infos.add("        Min temperature(°C) : " + Min[I]);
            City_Infos.add("        Max temperature(°C) : " + Max[I]);
            City_Infos.add("        Pressure(mm) : " + Pressure[I]);
            City_Infos.add("        Humidity(%) : " + Humidity[I]);
            City_Infos.add("        Description : " + Description[I]);

        }
        ArrayAdapter<String> List_I = new ArrayAdapter<String>(this, R.layout.info_list_view, R.id.Temp_Name, City_Infos);
        List_Info.setAdapter(List_I);

    }


}
