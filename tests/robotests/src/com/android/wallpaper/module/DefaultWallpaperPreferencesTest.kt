/*
 * Copyright (C) 2023 The Android Open Source Project
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
package com.android.wallpaper.module

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.wallpaper.R
import com.android.wallpaper.model.StaticWallpaperMetadata
import com.android.wallpaper.module.WallpaperPreferenceKeys.NoBackupKeys
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DefaultWallpaperPreferencesTest {

    private val wallpaperPreferences: DefaultWallpaperPreferences =
        DefaultWallpaperPreferences(ApplicationProvider.getApplicationContext())

    @Test
    fun setHomeStaticImageWallpaperMetadata_metadataShouldBeSavedToPreferences() {
        wallpaperPreferences.setHomeStaticImageWallpaperMetadata(
            StaticWallpaperMetadata(
                attributions = listOf("attr1", "attr2"),
                actionUrl = "http://www.google.com/",
                actionLabelRes = R.string.explore,
                actionIconRes = R.drawable.ic_explore_24px,
                collectionId = "cultural_events",
                hashCode = 10013L,
                managerId = 3,
                remoteId = "ocean",
            )
        )

        val pref =
            (ApplicationProvider.getApplicationContext() as Context).getSharedPreferences(
                DefaultWallpaperPreferences.PREFS_NAME,
                Context.MODE_PRIVATE
            )
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_ATTRIB_1, null))
            .isEqualTo("attr1")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_ATTRIB_2, null))
            .isEqualTo("attr2")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_ACTION_URL, null))
            .isEqualTo("http://www.google.com/")
        assertThat(
                pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_ACTION_LABEL_RES, null)
            )
            .isEqualTo("com.android.wallpaper:string/explore")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_ACTION_ICON_RES, null))
            .isEqualTo("com.android.wallpaper:drawable/ic_explore_24px")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_COLLECTION_ID, null))
            .isEqualTo("cultural_events")
        assertThat(pref.getLong(WallpaperPreferenceKeys.KEY_HOME_WALLPAPER_HASH_CODE, 0L))
            .isEqualTo(10013)
        assertThat(pref.getInt(NoBackupKeys.KEY_HOME_WALLPAPER_MANAGER_ID, 0)).isEqualTo(3)
        assertThat(pref.getString(NoBackupKeys.KEY_HOME_WALLPAPER_REMOTE_ID, null))
            .isEqualTo("ocean")
    }

    @Test
    fun setLockStaticImageWallpaperMetadata_metadataShouldBeSavedToPreferences() {
        wallpaperPreferences.setLockStaticImageWallpaperMetadata(
            StaticWallpaperMetadata(
                attributions = listOf("attr1", "attr2"),
                actionUrl = "http://www.google.com/",
                actionLabelRes = R.string.explore,
                actionIconRes = R.drawable.ic_explore_24px,
                collectionId = "cultural_events",
                hashCode = 10013L,
                managerId = 3,
                remoteId = "ocean",
            )
        )

        val pref =
            (ApplicationProvider.getApplicationContext() as Context).getSharedPreferences(
                DefaultWallpaperPreferences.PREFS_NAME,
                Context.MODE_PRIVATE
            )
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_ATTRIB_1, null))
            .isEqualTo("attr1")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_ATTRIB_2, null))
            .isEqualTo("attr2")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_ACTION_URL, null))
            .isEqualTo("http://www.google.com/")
        assertThat(
                pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_ACTION_LABEL_RES, null)
            )
            .isEqualTo("com.android.wallpaper:string/explore")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_ACTION_ICON_RES, null))
            .isEqualTo("com.android.wallpaper:drawable/ic_explore_24px")
        assertThat(pref.getString(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_COLLECTION_ID, null))
            .isEqualTo("cultural_events")
        assertThat(pref.getLong(WallpaperPreferenceKeys.KEY_LOCK_WALLPAPER_HASH_CODE, 0L))
            .isEqualTo(10013)
        assertThat(pref.getInt(NoBackupKeys.KEY_LOCK_WALLPAPER_MANAGER_ID, 0)).isEqualTo(3)
        assertThat(pref.getString(NoBackupKeys.KEY_LOCK_WALLPAPER_REMOTE_ID, null))
            .isEqualTo("ocean")
    }
}
