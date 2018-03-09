package coma.vouchreal.ggrt;

import android.os.Bundle;

public interface IPresenters {

    void onCreateView(Bundle saveInstance);

    void onStart();

    void onStop();

    void onDestroy();
}
