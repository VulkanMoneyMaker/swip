package com.fjjawinshop.giftaa;

import android.os.Bundle;

public interface MainPresenter {

    void onCreateView(Bundle saveInstance);

    void onStart();

    void onStop();

    void onDestroy();
}
