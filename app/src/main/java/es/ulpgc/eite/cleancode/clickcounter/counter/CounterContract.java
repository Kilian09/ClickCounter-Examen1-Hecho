package es.ulpgc.eite.cleancode.clickcounter.counter;

import java.lang.ref.WeakReference;


public interface CounterContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void navigateToNextScreen();
    void onDataUpdated(CounterViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);


    void onResume();
    void onStart();
    void onRestart();
    void onBackPressed();
    void onPause();
    void onDestroy();

    void onClicksPressed();
    void onResetPressed();
    void onIncrementPressed();
  }

  interface Model {
    void onDataFromNextScreen(String data);
    void onRestartScreen(String data);
    void onDataFromPreviousScreen(String data);
    int getCount();

    int getClickCount();

    void setCount(int count);

      void setClickCount(int clickCount);

      boolean isLastCount();

    void updateCount();

    void resetCount();

    void clickCount();
  }

}
