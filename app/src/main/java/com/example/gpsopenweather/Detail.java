package com.example.gpsopenweather;

public class Detail {
    private String description = "";
    private String temp = "";
    private String dt = "";
    private String temp_F = "";
    private String avghumidity = "";
    private String UV = "";
    private String imurl ;

    public Detail () {

    }
    public void  setIm(String imurl){
        this.imurl = imurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }


    public void setTemp_F(String temp_F){
        this.temp_F = temp_F;
    }

    public void setavghumidity(String avghumidity){
        this.avghumidity = avghumidity;
    }

    public void setUV(String UV){
        this.UV = UV;
    }



    public String getDescription(){
        return description;
    }


    public String getDt(){
        return dt;
    }

    public String getTemp(){
        return temp ;
    }
    public String getTemp_F(){
        return temp_F;
    }

    public String getavghumidity(){
        return avghumidity;
    }

    public String getUV(){
        return UV;
    }
    public String getimurl(){
        return imurl;
    }

}
