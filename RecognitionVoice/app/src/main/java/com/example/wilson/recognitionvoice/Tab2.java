package com.example.wilson.recognitionvoice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Wilson on 16/03/2017.
 */

public class Tab2 extends Fragment{
    public Tab2(){

    }

    @Override
    public void onCreate(Bundle savedinstance){
        super.onCreate(savedinstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedinstance){
        return inflater.inflate(R.layout.tab2,group,false);
    }
}
