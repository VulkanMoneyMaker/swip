package fonbet.svakionline.here;


import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class App extends android.app.Application {
    private static final String TAG = App.class.getSimpleName();
    private static final String AF_DEV_KEY = "scA85DtfhxLRsw6NpixhEF";
    AppsFlyerConversionListener conversionDataListener;
    @Override
    public void onCreate() {
        super.onCreate();
        conversionDataListener = new AppsFlyerConversionListener() {
            @Override
            public void onInstallConversionDataLoaded(Map<String, String> map) {
                Log.d(TAG, "onInstallConversionDataLoaded");
            }

            @Override
            public void onInstallConversionFailure(String s) {
                Log.d(TAG, "onInstallConversionFailure");
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                Log.d(TAG, "onAppOpenAttribution");
            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(TAG, "onAttributionFailure");
            }
        };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionDataListener, getApplicationContext());
        AppsFlyerLib.getInstance().startTracking(this);
    }
}
