package es.ulpgc.eite.cleancode.clickcounter.counter;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class CounterPresenter implements CounterContract.Presenter {

    public static String TAG = CounterPresenter.class.getSimpleName();

    private WeakReference<CounterContract.View> view;
    private CounterState state;
    private CounterContract.Model model;

    private AppMediator mediator;

    public CounterPresenter(AppMediator mediator) {
        this.mediator = mediator;
        state = mediator.getCounterState();
    }


    @Override
    public void onStart() {
        // Log.e(TAG, "onStart()");


        // initialize the state if is necessary
        if (state == null) {
            state = new CounterState();
        }

        // call the model and update the state
        state.count = String.valueOf(model.getCount());
        state.countEnable = true;
        state.clicksEnable = false;
        state.resetEnable = false;

        view.get().onDataUpdated(state);
        // state.data = model.getStoredData();

    /*
    // use passed state if is necessary
    PreviousToCounterState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);

      // update the state if is necessary
      state.data = savedState.data;
    }
    */
    }

    @Override
    public void onRestart() {
        // Log.e(TAG, "onRestart()");

        // update the model if is necessary
        //model.onRestartScreen(state.count);
    }

    @Override
    public void onResume() {
        // Log.e(TAG, "onResume()");

        // use passed state if is necessary
        ClicksToCounterState savedState = getStateFromNextScreen();
        if (savedState != null) {

            // update the model if is necessary

            state.clicks = String.valueOf(savedState.clicks);
            // update the state if is necessary

            model.setClickCount(Integer.parseInt(state.clicks));
        }

        // call the model and update the state
        //state.data = model.getStoredData();
        int Count = Integer.parseInt(state.count);
        model.setCount(Count);



        // update the view
        view.get().onDataUpdated(state);

    }


    @Override
    public void onBackPressed() {
        // Log.e(TAG, "onBackPressed()");

    }

    @Override
    public void onPause() {
        // Log.e(TAG, "onPause()");
    }

    @Override
    public void onDestroy() {
        // Log.e(TAG, "onDestroy()");
    }

    @Override
    public void onClicksPressed() {
        // Log.e(TAG, "onClicksPressed()");
        int clicks = model.getClickCount();
        CounterToClicksState newState = new CounterToClicksState(clicks);
        passStateToNextScreen(newState);
        view.get().navigateToNextScreen();

    }

    @Override
    public void onResetPressed() {
        // Log.e(TAG, "onResetPressed()");

        model.resetCount();
        int Count = model.getCount();

        state.count = String.valueOf(Count);
        state.resetEnable = false;

        view.get().onDataUpdated(state);
    }

    @Override
    public void onIncrementPressed() {
        // Log.e(TAG, "onIncrementPressed()");
        if (model.isLastCount()) {

            model.updateCount();

            state.resetEnable = true;

        } else {
            model.resetCount();

            state.resetEnable = false;
        }

        model.clickCount();

        state.clicksEnable = true;

        int Count = model.getCount();
        state.count = String.valueOf(Count);

        view.get().onDataUpdated(state);
    }

    private void passStateToNextScreen(CounterToClicksState state) {
        mediator.setCounterNextScreenState(state);
    }

    private ClicksToCounterState getStateFromNextScreen() {
        return mediator.getCounterNextScreenState();
    }

    @Override
    public void injectView(WeakReference<CounterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(CounterContract.Model model) {
        this.model = model;
    }

}
