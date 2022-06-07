package com.example.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class weatherAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Weather>weatherList;

    public weatherAdapter(Context context, int layout, List<Weather> weatherList) {
        this.context = context;
        this.layout = layout;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int i) {
        return weatherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        TextView time;
        TextView temp;
        TextView humidity;
        TextView cloud;
        TextView wind;
        ImageView icon;
        TextView status;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder=new ViewHolder();
        if(view==null)
        {
            view= inflater.inflate(layout,null);
            viewHolder.time=view.findViewById(R.id.textViewTime);
            viewHolder.temp=view.findViewById(R.id.tempRow);
            viewHolder.humidity=view.findViewById(R.id.textViewHumidityRow);
            viewHolder.cloud=view.findViewById(R.id.textViewCloudRow);
            viewHolder.wind=view.findViewById(R.id.textViewWindRow);
            viewHolder.icon=view.findViewById(R.id.imageViewWea);
            viewHolder.status=view.findViewById(R.id.statusRow);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) view.getTag();
        }
        Weather weather=weatherList.get(i);
        viewHolder.temp.setText(weather.getTemperature()+"°C");
        viewHolder.humidity.setText(weather.getHumidity()+"%");
        viewHolder.wind.setText(weather.getWind()+"m/s");
        String icon=weather.getIcon();
        Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@4x.png").into(viewHolder.icon);
        viewHolder.time.setText(weather.getTime());
        viewHolder.status.setText(weather.getStatus());
        return view;
    }
}
