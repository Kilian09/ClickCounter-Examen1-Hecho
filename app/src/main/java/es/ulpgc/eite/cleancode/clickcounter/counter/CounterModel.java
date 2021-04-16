package es.ulpgc.eite.cleancode.clickcounter.counter;

import android.util.Log;

public class CounterModel implements CounterContract.Model {

    public static String TAG = CounterModel.class.getSimpleName();

    private String data;

    public int countGeneral;

    public CounterModel(String data) {
        this.data = data;
    }


    @Override
    public void onRestartScreen(String data) {
        // Log.e(TAG, "onRestartScreen()");
    }

    @Override
    public void onDataFromNextScreen(String data) {
        // Log.e(TAG, "onDataFromNextScreen()");
    }

    @Override
    public void onDataFromPreviousScreen(String data) {
        // Log.e(TAG, "onDataFromPreviousScreen()");
    }

    @Override
    public int getCount() {
        return countGeneral;
    }


    @Override
    public void setCount(int count) {
      countGeneral = count;
    }
}
