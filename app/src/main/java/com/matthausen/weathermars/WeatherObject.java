package com.matthausen.weathermars;

public class WeatherObject {
    private String season;
    private String sol;
    private Double avgTemp;
    private Double minTemp;
    private Double maxTemp;
    private String windDir;

    public WeatherObject(String season, String sol, Double avgTemp, Double minTemp, Double maxTemp, String windDir) {
        this.season=season;
        this.sol=sol;
        this.avgTemp=avgTemp;
        this.minTemp=minTemp;
        this.maxTemp=maxTemp;
        this.windDir=windDir;
    }

    public String getSeason() {return season;}
    public String getSol() {return sol;}
    public Double getAvgTemp(){return avgTemp;}
    public Double getMinTemp() {return minTemp;}
    public Double getMaxTemp() {return maxTemp;}
    public String getWindDir() {return windDir;}
}
