package com.tqlweb.tqlsaldo.common;

import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public static final String APP_SETTING_FILES= "APP_SETTINGS";

    private SharedPreferencesManager(){

    }

    public static void cleanPreferences(){
        //SharedPreferences shred = MyApp.getContext().getSharedPreferences(APP_SETTING_FILES,MyApp.getContext().MODE_PRIVATE);
        //shred.edit().clear();

        SharedPreferences.Editor editor = getSharedPreferences().edit();
       editor.clear();
        editor.commit();

    }

    private static SharedPreferences getSharedPreferences(){

        SharedPreferences shred = MyApp.getContext().getSharedPreferences(APP_SETTING_FILES,MyApp.getContext().MODE_PRIVATE);

        return shred;

    }

    public static void  setSomeStringValue(String datalabel,String value){

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(datalabel,value);
        editor.commit();
    }

    public static void  setSomeBooleanValue(String datalabel,boolean value){

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        //editor.clear();
        editor.putBoolean(datalabel,value);
        editor.commit();
    }


    public static String getSomeStringvalue(String label){
        return getSharedPreferences().getString(label,null);
    }

    public static boolean getSomeBooleanvalue(String label){
        return getSharedPreferences().getBoolean(label,false);
    }
}
