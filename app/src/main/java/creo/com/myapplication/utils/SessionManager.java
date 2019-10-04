package creo.com.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {


    private SharedPreferences sharedPreferences;


    public SessionManager(Context  context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getSourcLat() {
        String lat = sharedPreferences.getString("lat","");

        return lat;
    }

    public void setSourceLat(String lat) {
    sharedPreferences.edit().putString("lat",lat).commit();
    }

    public String getBookinglat() {
        String lat = sharedPreferences.getString("blat","");

        return lat;
    }

    public void setBookinglong(String blong) {
        sharedPreferences.edit().putString("blong",blong).commit();
    }
    public String getBookinglong() {
        String lat = sharedPreferences.getString("blong","");

        return lat;
    }
    public void setlocation(String location) {
        sharedPreferences.edit().putString("location",location).commit();
    }
    public String getlocation() {
        String lat = sharedPreferences.getString("location","");

        return lat;
    }


    public void setBookinglat(String blat) {
        sharedPreferences.edit().putString("blat",blat).commit();
    }
    public void setBookingamount(String bam) {
        sharedPreferences.edit().putString("bam",bam).commit();
    }
    public String getBookingamount() {
        String lat = sharedPreferences.getString("bam","");

        return lat;
    }


    public String getID() {
        String lat = sharedPreferences.getString("ID","");

        return lat;
    }

    public void setID(String ID) {
        sharedPreferences.edit().putString("ID",ID).commit();
    }


    public void setSourceLong(String longt){
        sharedPreferences.edit().putString("longt",longt).commit();
    }

    public String getSourcLong(){
        return  sharedPreferences.getString("longt","");
    }
    public String getDestLat(){
        return  sharedPreferences.getString("latt","");
    }
    public void setDestLat(String latt){
        sharedPreferences.edit().putString("latt",latt).commit();
    }

    public void setDestLong(String longtt){
        sharedPreferences.edit().putString("longtt",longtt).commit();
    }
    public String getDestLong(){

            return  sharedPreferences.getString("longt","");
    }
    public void setDesttripLong(String dtlo){
        sharedPreferences.edit().putString("dtlo",dtlo).commit();
    }
    public String getDesttripLong(){

        return  sharedPreferences.getString("dtlo","");
    }

    public String getDesttripLat(){
        return  sharedPreferences.getString("lattrip","");
    }
    public void setDestriptLat(String lattrip){
        sharedPreferences.edit().putString("lattrip",lattrip).commit();
    }

    public void setUpdatelat(String uplat){
        sharedPreferences.edit().putString("uplat",uplat).commit();
    }
    public String getUpdatelat(){

        return  sharedPreferences.getString("uplat","");
    }
    public void setUpdateLong(String uplong){
        sharedPreferences.edit().putString("uplong",uplong).commit();
    }
    public String getUpdateLong(){

        return  sharedPreferences.getString("uplong","");
    }

    public void setScheduledlat(String slat){
        sharedPreferences.edit().putString("slat",slat).commit();
    }
    public String getScheduledlat(){

        return  sharedPreferences.getString("slat","");
    }
    public void setScheduledlong(String slon){
        sharedPreferences.edit().putString("slon",slon).commit();
    }
    public String getScheduledlong(){

        return  sharedPreferences.getString("slon","");
    }
    public void setSourcetriplat(String trla){
        sharedPreferences.edit().putString("trla",trla).commit();
    }
    public String getSourcetriplat(){

        return  sharedPreferences.getString("trla","");
    }
    public void setSourcetriplong(String trlo){
        sharedPreferences.edit().putString("trlo",trlo).commit();
    }
    public String getSourcetriplong(){

        return  sharedPreferences.getString("trlo","");
    }
   public void setTokens(String token){
        sharedPreferences.edit().putString("token",token).commit();
    }
    public String getTokens(){

        return  sharedPreferences.getString("token","");
    }
    public void setSavelat(String salat){
        sharedPreferences.edit().putString("salat",salat).commit();
    }
    public String getSavelat(){

        return  sharedPreferences.getString("salat","");
    }
    public void setSavelong(String salong){
        sharedPreferences.edit().putString("salong",salong).commit();
    }
    public String getSavelong(){

        return  sharedPreferences.getString("salong","");
    }
    public void setSourceAdd(String sourceadd){
        sharedPreferences.edit().putString("sourceadd",sourceadd).commit();
    }
    public String getSourceadd(){

        return  sharedPreferences.getString("sourceadd","");
    }
   /* public void setFitness(String vehicle_fitness){
        sharedPreferences.edit().putString("vehicle_fitness",vehicle_fitness).commit();
    }
    public String getFitness(){

        return  sharedPreferences.getString("vehicle_fitness","");
    }
    public void setNOC(String noc){
        sharedPreferences.edit().putString("noc",noc).commit();
    }
    public String getNOC(){

        return  sharedPreferences.getString("noc","");
    }
    public void setPho(String img){
        sharedPreferences.edit().putString("img",img).commit();
    }
    public String getPho(){

        return  sharedPreferences.getString("img","");
    }
*/
}
