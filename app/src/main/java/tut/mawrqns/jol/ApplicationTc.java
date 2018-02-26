package tut.mawrqns.jol;

import android.app.Application;
import android.arch.persistence.room.Room;

import tut.mawrqns.jol.db.AppDatabase;
import tut.mawrqns.jol.db.RoomHelper;

public class ApplicationTc extends Application {

    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        getDataBase();
    }

    private void getDataBase() {
        appDatabase = Room.databaseBuilder(this,
                AppDatabase.class, "balance-bd")
                .addCallback(new RoomHelper())
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
