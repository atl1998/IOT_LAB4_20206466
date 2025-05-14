package com.example.lab4_20206466;

public class Forecast {
    private String date;
    private double maxTempC;
    private double minTempC;
    private double avgTempC;
    private String conditionWeather;
    private String urlIcon;
    private double maxWindSpeed;
    private double chanceOfRain;

    public String getDate() { return date; }
    public double getMaxTempC() { return maxTempC; }
    public double getMinTempC() { return minTempC; }
    public double getAvgTempC() { return avgTempC; }
    public String getConditionWeather() { return conditionWeather; }
    public String getUrlIcon() { return urlIcon; }
    public double getMaxWindSpeed() { return maxWindSpeed; }
    public double getChanceOfRain() { return chanceOfRain; }

    public void setDate(String date) { this.date = date; }
    public void setMaxTempC(double maxTempC) { this.maxTempC = maxTempC; }
    public void setMinTempC(double minTempC) { this.minTempC = minTempC; }
    public void setAvgTempC(double avgTempC) { this.avgTempC = avgTempC; }
    public void setConditionWeather(String conditionWeather) { this.conditionWeather = conditionWeather; }
    public void setUrlIcon(String urlIcon) { this.urlIcon = urlIcon; }
    public void setMaxWindSpeed(double maxWindSpeed) { this.maxWindSpeed = maxWindSpeed; }
    public void setChanceOfRain(double chanceOfRain) { this.chanceOfRain = chanceOfRain; }
}
