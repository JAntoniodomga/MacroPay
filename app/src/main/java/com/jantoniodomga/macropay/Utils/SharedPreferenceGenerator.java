package com.jantoniodomga.macropay.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceGenerator{
    private static SharedPreferences instance=null;

    public static SharedPreferences getInstance(Context context){
        if (instance==null)
            instance=context.getSharedPreferences("session",Context.MODE_PRIVATE);
        return instance;
    }
}
