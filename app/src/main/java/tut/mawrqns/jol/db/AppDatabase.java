package tut.mawrqns.jol.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {BalanceEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BalanceDao balanceDao();
}
