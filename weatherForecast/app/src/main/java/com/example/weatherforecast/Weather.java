package com.example.weatherforecast;

import java.io.Serializable;
import java.util.Date;

public class Weather implements Serializable {
    String time;
    String temperature;
    String icon;
    String humidity;
    String cloud;
    String wind;
    String status;
    public Weather() {
    }

    public Weather(String time, String temperature, String icon, String humidity, String cloud, String wind,String status) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
        this.humidity = humidity;
        this.cloud = cloud;
        this.wind = wind;
        this.status=status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
