/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.wallpaper.module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.wallpaper.module.logging.UserEventLogger;
import com.android.wallpaper.util.DiskBasedLogger;

/**
 * Performs daily logging operations when alarm is received.
 */
public class DailyLoggingAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Context appContext = context.getApplicationContext();
        Injector injector = InjectorProvider.getInjector();
        UserEventLogger logger = injector.getUserEventLogger(appContext);
        WallpaperPreferences preferences = injector.getPreferences(appContext);

        logger.logSnapshot();

        preferences.setLastDailyLogTimestamp(System.currentTimeMillis());

        // Clear disk-based logs older than 7 days if they exist.
        DiskBasedLogger.clearOldLogs(appContext);
    }
}
