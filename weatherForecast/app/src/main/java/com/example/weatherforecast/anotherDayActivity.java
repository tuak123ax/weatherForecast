package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class anotherDayActivity extends AppCompatActivity {

    ArrayList<Weather>weatherArrayList;
    weatherAdapter adapter;
    TextView location;
    ListView listview;
    Double lat=0.0;
    Double lng=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_day);
        anhXa();
        listview.setAdapter(adapter);
        String locationText=getIntent().getStringExtra("location");
        location.setText(locationText);
        get7DaysWeather(locationText);
    }
    public void get7DaysWeather(String data)
    {
        RequestQueue queue= Volley.newRequestQueue(anotherDayActivity.this);

        String urlcage="https://api.opencagedata.com/geocode/v1/json?q="+data+"&key=419ff87516d3488f9dd8d71f756a0893&pretty=1";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, urlcage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray resultArray=object.getJSONArray("results");
                    JSONObject result=resultArray.getJSONObject(0);
                    JSONObject geometry=result.getJSONObject("geometry");
                    lat=geometry.getDouble("lat");
                    lng=geometry.getDouble("lng");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);

        String url="https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lng+"&units=metric&appid=22e05e1f686df2bc648231d161d04ead";
        StringRequest stringRequest2=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    location.setText(MainActivity.cityName);
                    JSONArray list=object.getJSONArray("daily");
                    for(int i=0;i<list.length();i++)
                    {
                        JSONObject jsonObject= list.getJSONObject(i);

                        String time= jsonObject.getString("dt");
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = formatter.format(new Date(Long.valueOf(time)*1000L));

                        JSONObject temperatureObject= (JSONObject) jsonObject.get("temp");
                        String temperatureText=temperatureObject.getString("day");

                        String humiditytext=jsonObject.getString("humidity");

                        JSONArray arrayObject= (JSONArray) jsonObject.get("weather");
                        JSONObject objectWeather= (JSONObject) arrayObject.get(0);
                        String status=objectWeather.getString("description");
                        String icon=objectWeather.getString("icon");

                        String cloudText=jsonObject.getString("clouds");

                        String windText=jsonObject.getString("wind_speed");

                        Weather weather=new Weather(dateString,temperatureText,icon,humiditytext,cloudText,windText,status);
                        weatherArrayList.add(weather);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest2);
    }
    public void anhXa()
    {
        location=(TextView)findViewById(R.id.textViewLocation);
        weatherArrayList=new ArrayList<>();
        adapter=new weatherAdapter(anotherDayActivity.this,R.layout.weather_row,weatherArrayList);
        listview=(ListView)findViewById(R.id.listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.back)
        {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}