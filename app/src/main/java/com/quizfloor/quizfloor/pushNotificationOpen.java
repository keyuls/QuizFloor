package com.quizfloor.quizfloor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by keyul on 12/31/2015.
 */
public class pushNotificationOpen extends ParsePushBroadcastReceiver {

    protected Class<? extends Activity> getActivity(Context context,
                                                    Intent intent) {
        return showChallenges.class;
    }
}
