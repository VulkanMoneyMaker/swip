package tut.mawrqns.jol.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import io.reactivex.Single;

@Dao
public interface BalanceDao {

    @Query("SELECT * FROM balance_table")
    Single<BalanceEntity> getBalance();

    @Update
    void update(BalanceEntity balanceEntity);
}
