package es.ulpgc.eite.cleancode.clickcounter.clicks;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.clickcounter.app.AppMediator;
import es.ulpgc.eite.cleancode.clickcounter.app.ClicksToCounterState;
import es.ulpgc.eite.cleancode.clickcounter.app.CounterToClicksState;

public class ClicksPresenter implements ClicksContract.Presenter {

    public static String TAG = ClicksPresenter.class.getSimpleName();

    private WeakReference<ClicksContract.View> view;
    private ClicksState state;
    private ClicksContract.Model model;
    private AppMediator mediator;

    public ClicksPresenter(AppMediator mediator) {
        this.mediator = mediator;
        state = mediator.getClicksState();
    }


    @Override
    public void onStart() {
        // Log.e(TAG, "onStart()");

        // initialize the state if is necessary
        if (state == null) {
            state = new ClicksState();
        }

        // use passed state if is necessary
        CounterToClicksState savedState = getStateFromPreviousScreen();
        if (savedState != null) {

            state.clickCount = String.valueOf(savedState.clicks);

            state.clearEnabled = true;

        }

        view.get().onDataUpdated(state);
    }

    @Override
    public void onRestart() {
        // Log.e(TAG, "onRestart()");

        // update the model if is necessary
        // model.onRestartScreen(state.data);
    }


    @Override
    public void onResume() {
        // Log.e(TAG, "onResume()");

        int ClickCount = Integer.parseInt(state.clickCount);
        model.setCountClicks(ClickCount);



        // update the view
        view.get().onDataUpdated(state);

    }

    @Override
    public void onBackPressed() {
        // Log.e(TAG, "onBackPressed()");

        int clicks = model.getCountClicks();
        ClicksToCounterState newState = new ClicksToCounterState(clicks);
        passStateToPreviousScreen(newState);
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
    public void onClearPressed() {
        // Log.e(TAG, "onClearPressed()");

        model.resetClicks();

        state.clickCount = String.valueOf(model.getCountClicks());

        state.clearEnabled = false;

        view.get().onDataUpdated(state);
    }

    private void passStateToPreviousScreen(ClicksToCounterState state) {
        mediator.setClicksPreviousScreenState(state);
    }

    private CounterToClicksState getStateFromPreviousScreen() {
        return mediator.getClicksPreviousScreenState();
    }

    @Override
    public void injectView(WeakReference<ClicksContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(ClicksContract.Model model) {
        this.model = model;
    }

}
