package com.ljj.commonlib.kit.badge.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ljj.commonlib.kit.badge.Badger;
import com.ljj.commonlib.kit.badge.ShortcutBadgeException;
import com.ljj.commonlib.kit.badge.util.BroadcastHelper;

import java.util.Arrays;
import java.util.List;


/**
 * @author Leo Lin
 * Deprecated, LG devices will use DefaultBadger
 */
@Deprecated
public class LGHomeBadger implements Badger {

    private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";

    @Override
    public void executeBadge(Context context, ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, badgeCount);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());
        if(BroadcastHelper.canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
        } else {
            throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
        }
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.lge.launcher",
                "com.lge.launcher2"
        );
    }
}
