package com.example.pets.Handelers;


import android.content.Context;
import android.content.SharedPreferences;

import java.net.ContentHandler;

public class SaveState {

    Context context;
    String saveName;
    SharedPreferences sp;

    public SaveState(Context context, String saveName) {
        this.context = context;
        this.saveName = saveName;
        sp = context.getSharedPreferences(saveName, Context.MODE_PRIVATE);
    }

    public void setState(int key){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Key",key);
        editor.apply();
    }

    public int getState(){
        return sp.getInt("Key",0);
    }
}

