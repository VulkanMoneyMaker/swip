package stavki.bukmekerskaya.jfdha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                NotificationScheduler.setReminder(context, AlarmReceiver.class,
                        ConstantTime.hour, ConstantTime.minute);
                return;
            }
        }

        NotificationScheduler.showNotification(context, SplashScreenActvity.class,
                "Новые игры, новые ставки!",
                "Новые матчи через 20 минут, ЗАЛЕТАЙ");

    }
}
