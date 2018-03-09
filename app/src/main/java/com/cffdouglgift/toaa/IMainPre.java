package com.cffdouglgift.toaa;

import android.os.Bundle;

public interface IMainPre {

    void start(Bundle saveInstance);

    void preStart();

    void preStop();

    void preDestroy();
}
