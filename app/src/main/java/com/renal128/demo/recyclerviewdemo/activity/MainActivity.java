package com.renal128.demo.recyclerviewdemo.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.renal128.demo.recyclerviewdemo.R;

import java.util.Random;


public class MainActivity extends FragmentActivity
        implements PopUpFragment.OnFragmentInteractionListener
        {

    Handler messageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Fragment newFragment = PopUpFragment.newInstance(1500);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, newFragment, PopUpFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public void addMessage(View view) {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null){
            String text = "Message " + new Random().nextGaussian();
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
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if (popup != null){
            for (int i = 0; i<5; i++){
                popup.addMessage0ToPopUp(i%3, "Item №" + i);
            }

        }
    }

            @Override
    public void hidePopUpFrugment() {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(popup)
                    .commit();
        }
    }

    @Override
    public void showPopUpFrugment() {
        PopUpFragment popup  = (PopUpFragment)getSupportFragmentManager()
                .findFragmentByTag(PopUpFragment.class.getSimpleName());
        if(popup != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(popup)
                    .commit();
        }
    }
}
