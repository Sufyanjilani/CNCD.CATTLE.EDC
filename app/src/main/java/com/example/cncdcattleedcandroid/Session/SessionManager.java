package com.example.cncdcattleedcandroid.Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class SessionManager {
    public SharedPreferences sharedPreferences;

    private Context context;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    int PRIVATE_MODE = 0;



    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("SessionData",context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setthemstate(boolean b){
        editor.putBoolean("isthemedark",b);
        editor.apply();
        editor.commit();

    }

    public  void savestartlocation(String latitude,String longitude ){
        editor.putString("startlatitude",latitude);
        editor.putString("startlongitude",longitude);
        editor.apply();
        editor.commit();
    }
    public void savebarearToken(String baerertoken){
        editor.putString("bearer_token",baerertoken);
        editor.apply();
        editor.commit();
    }
    public boolean getthemstate(){

        return  sharedPreferences.getBoolean("isthemedark",false);
    }
    public boolean getstartlocation(){
        return sharedPreferences.getBoolean("latitude",false);
    }
    public boolean getlongitude(){
        return  sharedPreferences.getBoolean("longitude",false);
    }
    public String getbearer(){
        return sharedPreferences.getString("bearer_token","null");
    }
    public void isdarkThemeEnabled(Boolean enabled){


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("tag","dark");
    }


    public void SavePrimarykey(int primarykey){

        editor.putInt("primarykey",primarykey);
        editor.commit();
    }

    public Integer getPrimaryKey(){

        return sharedPreferences.getInt("primarykey",0);
    }

    public void ApplicationFirstTime(){

        editor.putBoolean("installedFirstTime",false);
        editor.apply();
        editor.commit();
    }



    public void saveStartCoordinatesAndTime(double startcoordinates_lat, double endcoordinates_lon,String startTime){

        editor.putString("start_coordinates_lat",String.valueOf(startcoordinates_lat));
        editor.putString("start_coordinates_lon",String.valueOf(endcoordinates_lon));
        editor.putString("start_time",startTime);
        editor.apply();
        editor.commit();
    }


    public String getStartTimestamp(){
        return sharedPreferences.getString("start_time","00/00/0000 00:00:00");
    }

    public String getLatitudeStart(){

        return sharedPreferences.getString("start_coordinates_lat","0.0");
    }

    public String getLongitudeStart(){

        return sharedPreferences.getString("start_coordinates_lon","0.0");
    }
    public Boolean checkisApplicationFirstTime(){
        return sharedPreferences.getBoolean("installedFirstTime",true);
    }

    public void saveDashboardFarmFarmerId(
            String farmId,
            String farmerId
    ){
        editor.putString("farmId",farmId);
        editor.putString("farmerId",farmerId);
        editor.commit();
    }

    public String getFarmId(){
        return sharedPreferences.getString("farmId","0").toString();
    }

    public String getFarmerId(){
        return sharedPreferences.getString("farmerId","0").toString();
    }

    public void Save_Farm_and_Farmer_ID(

            String farmer_ID,
            String farm_ID
    ) {

        editor.putString("farmer_ID",farmer_ID);
        editor.putString("farm_ID",farm_ID);
        editor.apply();
        editor.commit();
    }

    public String get_Farmer_ID(){

        return sharedPreferences.getString("farmer_ID","0").toString();

    }

    public String get_Farm_ID(){

        return sharedPreferences.getString("farm_ID","0").toString();
    }

    public void SaveUserData(

            String name,
            String email,
            String type,
            String userid

    ){

        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("type",type);
        editor.putString("userId",userid);
        editor.commit();

    }

    public void saveFarmerData(
            String farmerName,
            String farmName,
            String farmAddress,
            String farmSector,
            String mobileNumber,
            String altNumber
    ){
        editor.putString("farmerName",farmerName);
        editor.putString("farmName",farmName);
        editor.putString("farmAddress",farmAddress);
        editor.putString("farmSector",farmSector);
        editor.putString("mobileNumber",mobileNumber);
        editor.putString("altNumber",altNumber);
        editor.commit();
    }

    public String getFarmerName(){
        return sharedPreferences.getString("farmerName","null");
    }

    public String getFarmName(){
        return sharedPreferences.getString("farmName","null");
    }

    public String getFarmAddress(){
        return sharedPreferences.getString("farmAddress","null");
    }

    public String getFarmSector(){
        return sharedPreferences.getString("farmSector","null");
    }

    public String getMobileNumber(){
        return sharedPreferences.getString("mobileNumber","null");
    }

    public String getAltNumber(){
        return sharedPreferences.getString("altNumber","null");
    }

    public String getName(){

        return sharedPreferences.getString("name","null");
    }

    public String getEmail(){

        return  sharedPreferences.getString("email","null");
    }

    public String gettype(){

        return  sharedPreferences.getString("type","null");
    }

    public String getuserId(){

        return sharedPreferences.getString("userId",null);
    }

    public void SaveCattleID(String cattleId){

       editor.putString("cattleId",cattleId);
       editor.commit();
       editor.apply();

    }

    public String getCattleId(){

        return sharedPreferences.getString("cattleId","null");
    }

    public void saveCattleDetails(
            String cattleType,
            String cattleGender,
            String cattleBreed,
            String cattleSampleId
    ){
        editor.putString("cattleType",cattleType);
        editor.putString("cattleGender",cattleGender);
        editor.putString("cattleBreed",cattleBreed);
        editor.putString("cattleSampleId",cattleSampleId);
        editor.commit();
        editor.apply();
    }

    public String getcattleType(){

        return sharedPreferences.getString("cattleType",null);
    }
    public String getcattleGender(){

        return sharedPreferences.getString("cattleGender",null);
    }
    public String getcattleBreed(){

        return sharedPreferences.getString("cattleBreed",null);
    }
    public String getcattleSampleId(){

        return sharedPreferences.getString("cattleSampleId",null);
    }

}




