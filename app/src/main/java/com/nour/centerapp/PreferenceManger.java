package com.nour.centerapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManger {
    private SharedPreferences preferences;

    public PreferenceManger(Context context)
    {
        preferences=context.getSharedPreferences(Contains.NAME_FILE,Context.MODE_PRIVATE);
    }

    // to put Data(Name, E-mail,....)
    public void PutSting(String key,String data)
    {
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString(key,data);
        editor.apply();

    }

    // to get Data(Name , E-mail,.....)
    public String getDataString(String key)
    {
        return preferences.getString(key,null);
    }

    // to put name of Status is Sign in or not
    public void PutBoolean(String key, Boolean data)
    {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(key,data);
        editor.apply();
    }

    // to get data Status
    public Boolean getDataBoolean(String key)
    {
        return preferences.getBoolean(key,false);
    }



}
