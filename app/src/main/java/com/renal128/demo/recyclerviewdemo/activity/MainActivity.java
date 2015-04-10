package com.renal128.demo.recyclerviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.renal128.demo.recyclerviewdemo.R;

import java.util.Random;


public class MainActivity extends FragmentActivity
        implements PopUpFragment.OnFragmentInteractionListener
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void showRecycleView(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }

    public void showCardView(View view) {
        Intent intent = new Intent(this, CardViewActivity.class);
        startActivity(intent);
    }

    public void showFragment(View view) {

        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());

        if(popup == null) {
            Fragment newFragment = PopUpFragment.newInstance(5000);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, newFragment, PopUpFragment.class.getSimpleName())
                    .commit();
        }
    }

    public void addMessage(View view) {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null){
            String text = "Message " + new Random().nextInt();
            int icon = Math.abs(new Random().nextInt() % 3);
            popup.addMessage0ToPopUp(icon, text);
        }
    }

    public void removeMessage(View view) {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null){
            popup.removeMessagePopUp();
        }
    }


    @Override
    public void onPopUpFragmentStart() {

        PopUpFragment popup = (PopUpFragment) getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());

        if (popup != null) {
            for (int i = 0; i < 5; i++) {
                popup.addMessage0ToPopUp(i % 3, "Item №" + i);
            }

        }
    }





    @Override
    public void onHidePopUpFrugment() {
        /*PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(popup)
                    .commit();
        }*/
    }

    @Override
    public void onShowPopUpFrugment() {
        /*
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(popup)
                    .commit();
        }
        */
    }
}
