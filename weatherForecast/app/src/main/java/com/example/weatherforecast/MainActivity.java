package com.example.weatherforecast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText searchText;
    ImageButton buttonSearch;
    TextView city,country;
    TextView temperature,timeText;
    ImageView weather;
    TextView humidity,cloud,wind;
    Button anotherDay;
    TextView status;
    public static String cityName;
    public static Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        database.QueryData("CREATE TABLE IF NOT EXISTS History(Id INTEGER PRIMARY KEY AUTOINCREMENT, ls VARCHAR(200))");

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data=searchText.getText().toString();
                if(data.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Hãy nhập vị trí!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    database.QueryData("INSERT INTO History VALUES(null,'"+data+"')");
                    getCurrentWeather(data);
                }
            }
        });
        anotherDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchText.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Hãy nhập vị trí!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,anotherDayActivity.class);
                    intent.putExtra("location",searchText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    public void getCurrentWeather(String data)
    {
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        String url="https://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=22e05e1f686df2bc648231d161d04ead";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    cityName= jsonObject.getString("name");
                    city.setText("Thành phố: "+cityName);

                    String time= jsonObject.getString("dt");
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = formatter.format(new Date(Long.valueOf(time)*1000L));
                    timeText.setText(dateString);

                    JSONObject countryObject= (JSONObject) jsonObject.get("sys");
                    String countryText=countryObject.getString("country");
                    country.setText("Quốc gia: "+countryText);

                    JSONObject temperatureObject= (JSONObject) jsonObject.get("main");
                    String temperatureText=temperatureObject.getString("temp");
                    temperature.setText(temperatureText+"°C");

                    String humiditytext=temperatureObject.getString("humidity");
                    humidity.setText(humiditytext+"%");

                    JSONArray arrayObject= (JSONArray) jsonObject.get("weather");
                    JSONObject objectWeather= (JSONObject) arrayObject.get(0);
                    String sta=objectWeather.getString("description");
                    status.setText(sta);

                    String icon=objectWeather.getString("icon");
                    Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@4x.png").into(weather);
                    JSONObject objectCloud= jsonObject.getJSONObject("clouds");
                    String cloudText=objectCloud.getString("all");
                    cloud.setText(cloudText+"%");

                    JSONObject windObject= (JSONObject) jsonObject.get("wind");
                    String windText=windObject.getString("speed");
                    wind.setText(windText+"m/s");
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
    }
    public void anhXa()
    {
        searchText=(EditText)findViewById(R.id.searchText);
        buttonSearch=(ImageButton)findViewById(R.id.search_button);
        city=(TextView)findViewById(R.id.cityName);
        country=(TextView)findViewById(R.id.countryName);
        temperature=(TextView)findViewById(R.id.temperature);
        timeText=(TextView)findViewById(R.id.time);
        weather=(ImageView)findViewById(R.id.imageViewWeather);
        humidity=(TextView)findViewById(R.id.textViewHumidity);
        cloud=(TextView)findViewById(R.id.textViewCloud);
        wind=(TextView)findViewById(R.id.textViewWind);
        anotherDay=(Button)findViewById(R.id.anotherDay);
        status=(TextView)findViewById(R.id.status);
        database=new Database(MainActivity.this,"HistoryWeather",null,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.history)
        {
            Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(intent);
        }
        return true;
    }
}