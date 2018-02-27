package ghh.auus.fhhh.com;

import android.os.Bundle;

public interface UserPresenter {

    void onCreateView(Bundle saveInstance);

    void onStart();

    void onStop();

    void onDestroy();
}
