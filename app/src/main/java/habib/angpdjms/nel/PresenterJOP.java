package habib.angpdjms.nel;

import android.os.Bundle;

public interface PresenterJOP {

    void onCreateView(Bundle saveInstance);

    void onStart();

    void onStop();

    void onDestroy();

    void onShow();

    void  onLow();
}
