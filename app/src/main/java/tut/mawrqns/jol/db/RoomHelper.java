package tut.mawrqns.jol.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.support.annotation.NonNull;

public class RoomHelper extends RoomDatabase.Callback {

    private static final String BALANCE = "balance";
    private static final String BALANCE_PRE = "pre_balance";
    private static final String TIME_PLUS = "timePlus";
    private static final String TABLE_NAME = "balance_table";

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
        super.onCreate(db);
            ContentValues contentValues = new ContentValues();
            contentValues.put(BALANCE, 300);
            contentValues.put(BALANCE_PRE, 0);
            contentValues.put(TIME_PLUS, 0);
            db.insert(TABLE_NAME, OnConflictStrategy.IGNORE, contentValues);

    }

}
