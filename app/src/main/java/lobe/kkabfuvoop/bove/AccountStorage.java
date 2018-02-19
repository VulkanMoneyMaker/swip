package lobe.kkabfuvoop.bove;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class AccountStorage {
    private static final String PREF_ACCOUNT_NUMBER = "account_number";
    private static final String DEFAULT_ACCOUNT_NUMBER = "00000000";
    private static final String TAG = "AccountStorage";
    private static String sAccount = null;
    private static final Object sAccountLock = new Object();

    @SuppressLint("ApplySharedPref")
    public static void SetAccount(Context c, String s) {
        try{
            synchronized(sAccountLock) {
                Log.i(TAG, "Setting account number: " + s);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                prefs.edit().putString(PREF_ACCOUNT_NUMBER, s).commit();
                sAccount = s;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static String GetAccount(Context c) {
        try {
            synchronized (sAccountLock) {
                if (sAccount == null) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                    String account = prefs.getString(PREF_ACCOUNT_NUMBER, DEFAULT_ACCOUNT_NUMBER);
                    sAccount = account;
                }
                return sAccount;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
