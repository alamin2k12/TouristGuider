package com.example.dilshad.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DownloadForecast extends AsyncTask<String, Void, String>
{
    String getPressure[]=new String[3];
    String getTemperature[]=new String[3];
    String getDate[] = new String[3];
    String getHumidity[] = new String[3];
    String getDay[] = new String[3];
    String getNight[] = new String[3];
    String getDes[] = new String[3];
    String getMorning[] = new String[3];
    String getEvening[] = new String[3];
    String getMin[] = new String[3];
    String getMax[] = new String[3];

    private static final String DEBUG_TAG = "HttpExample";
    int id;
    String description = "";
    String temperature = "";
    String windSpeed = "";
    String humidity = "";
    String pressure = "";
    float temp;

    public interface AsyncResponses{
        void processFinishing(String[] getDay, String[] getNight,String[] getMin, String[] getMax, String[] getMorning, String[] getEvening, String[] getDate, String[] getPres, String[] hum, String[] des);
    }

   public AsyncResponses delegating = null;

    @Override
    protected String doInBackground(String... urls)
    {

        // params comes from the execute() call: params[0] is the url.
        try
        {
            return downloadUrl(urls[0]);
        }
        catch (IOException e)
        {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }


    @Override
    protected void onPostExecute(String result) {
        String data = "";
        // Forecast.process();
        try {
            JSONObject jsonRootObject = new JSONObject(result);


            JSONArray jsonArray = jsonRootObject.optJSONArray("list");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);


                long id = Integer.parseInt(jsonObject.optString("dt"));
                long unixSeconds = id;
                Date date = new Date(unixSeconds * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
                String formattedDate = sdf.format(date);
                getDate[i] = formattedDate;


                // Getting temperature value
                JSONObject tem = jsonObject.getJSONObject("temp");
                getDay[i] = tem.getString("day");
                getNight[i] = tem.getString("night");
                getMin[i] = tem.getString("min");
                getMax[i] = tem.getString("max");
                getMorning[i] = tem.getString("morn");
                getEvening[i] = tem.getString("eve");




                getPressure[i] = jsonObject.optString("pressure");
                getHumidity[i] = jsonObject.optString("humidity");



                JSONArray jsonArray1 = jsonObject.optJSONArray("weather");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    getDes[i] = jsonObject1.optString("description");
                }


                delegating.processFinishing(getDay, getNight, getMin, getMax, getMorning, getEvening, getDate, getPressure, getHumidity, getDes);
            }







        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downloadUrl(String myurl) throws IOException
    {
        InputStream is = null;


        int len = 100000;

        try
        {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();

            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();


            String contentAsString = readIt(is, len);



            return contentAsString;


        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}


