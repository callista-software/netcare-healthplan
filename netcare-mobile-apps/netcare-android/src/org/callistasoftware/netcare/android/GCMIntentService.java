package org.callistasoftware.netcare.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;
import org.callistasoftware.netcare.android.helper.ApplicationHelper;
import org.callistasoftware.netcare.android.helper.AuthHelper;
import org.callistasoftware.netcare.android.helper.GCMHelper;
import org.callistasoftware.netcare.android.helper.RestHelper;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	protected void onError(Context arg0, String arg1) {}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received push message: " + intent.getAction());
		final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		final String subject = intent.getExtras().getString("title");
		final String message = intent.getExtras().getString("message");
		final String when = intent.getExtras().getString("timestamp");
		
		final Intent notificationIntent = new Intent(context, StartActivity.class);
		final PendingIntent contentIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, notificationIntent, 0);
		
		final Notification notification = new Notification(R.drawable.mhp_icon, message, Long.valueOf(when));
		notification.defaults |= Notification.DEFAULT_ALL;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(context.getApplicationContext(), subject, message, contentIntent);
		notificationManager.notify(1, notification);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Received push registration message. Registration id is: " + registrationId);
        GCMHelper.newInstance(getApplicationContext()).publishRegistrationId(registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.d(TAG, "Received push unregistration message. Registration id is: " + regId);
        GCMHelper.newInstance(getApplicationContext()).unpublishRegistrationId(regId);
	}

}
