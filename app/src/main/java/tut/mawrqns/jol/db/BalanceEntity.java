package tut.mawrqns.jol.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

@android.arch.persistence.room.Entity(tableName = "balance_table")
public class BalanceEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "balance")
    private int balance;

    @ColumnInfo(name = "pre_balance")
    private int balancePre;

    @ColumnInfo(name = "timePlus")
    private int timePlus;

    public void setId(int id) {
        this.id = id;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setTimePlus(int timePlus) {
        this.timePlus = timePlus;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public int getTimePlus() {
        return timePlus;
    }

    public int getBalancePre() {
        return balancePre;
    }

    public void setBalancePre(int balancePre) {
        this.balancePre = balancePre;
    }
}
