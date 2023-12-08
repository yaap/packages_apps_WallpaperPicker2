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
package com.android.wallpaper.model;

import static android.app.WallpaperManager.SetWallpaperFlags;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Parcel;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.android.wallpaper.asset.Asset;
import com.android.wallpaper.asset.BuiltInWallpaperAsset;
import com.android.wallpaper.asset.CurrentWallpaperAssetVN;
import com.android.wallpaper.module.InjectorProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the currently set wallpaper on N+ devices. Should not be used to set a new wallpaper.
 */
public class CurrentWallpaperInfo extends WallpaperInfo {

    public static final Creator<CurrentWallpaperInfo> CREATOR =
            new Creator<CurrentWallpaperInfo>() {
                @Override
                public CurrentWallpaperInfo createFromParcel(Parcel source) {
                    return new CurrentWallpaperInfo(source);
                }

                @Override
                public CurrentWallpaperInfo[] newArray(int size) {
                    return new CurrentWallpaperInfo[size];
                }
            };
    private static final String TAG = "CurrentWallpaperInfoVN";
    private final List<String> mAttributions;
    private Asset mAsset;
    private final String mActionUrl;
    @StringRes
    private int mActionLabelRes;
    @DrawableRes
    private int mActionIconRes;
    private final String mCollectionId;
    @SetWallpaperFlags
    private final int mWallpaperManagerFlag;
    public static final String UNKNOWN_CURRENT_WALLPAPER_ID = "unknown_current_wallpaper_id";

    /**
     * Constructs a new instance of this class.
     *
     * @param wallpaperManagerFlag Either SYSTEM or LOCK--the source of image data which this object
     *                             represents.
     */
    public CurrentWallpaperInfo(List<String> attributions, String actionUrl,
                                  @StringRes int actionLabelRes, @DrawableRes int actionIconRes,
                                  String collectionId,
                                  @SetWallpaperFlags int wallpaperManagerFlag) {
        mAttributions = attributions;
        mWallpaperManagerFlag = wallpaperManagerFlag;
        mActionUrl = actionUrl;
        mActionLabelRes = actionLabelRes;
        mActionIconRes = actionIconRes;
        mCollectionId = collectionId;
    }

    private CurrentWallpaperInfo(Parcel in) {
        super(in);
        mAttributions = new ArrayList<>();
        in.readStringList(mAttributions);
        //noinspection ResourceType
        mWallpaperManagerFlag = in.readInt();
        mActionUrl = in.readString();
        mCollectionId = in.readString();
        mActionLabelRes = in.readInt();
        mActionIconRes = in.readInt();
    }

    @Override
    public String getWallpaperId() {
        return UNKNOWN_CURRENT_WALLPAPER_ID + mWallpaperManagerFlag;
    }

    @Override
    public List<String> getAttributions(Context context) {
        return mAttributions;
    }

    @Override
    public Asset getAsset(Context context) {
        if (mAsset == null) {
            mAsset = createCurrentWallpaperAssetVN(context);
        }
        return mAsset;
    }

    @Override
    public Asset getThumbAsset(Context context) {
        return getAsset(context);
    }

    @Override
    public String getActionUrl(Context unused) {
        return mActionUrl;
    }

    @Override
    public String getCollectionId(Context unused) {
        return mCollectionId;
    }

    @Override
    public int getActionIconRes(Context unused) {
        return mActionIconRes != 0 ? mActionIconRes : WallpaperInfo.getDefaultActionIcon();
    }

    @Override
    public int getActionLabelRes(Context unused) {
        return mActionLabelRes != 0 ? mActionLabelRes : WallpaperInfo.getDefaultActionLabel();
    }

    /**
     * Constructs and returns an Asset instance representing the currently-set wallpaper asset.
     */
    private Asset createCurrentWallpaperAssetVN(Context context) {
        // Whether the wallpaper this object represents is the default built-in wallpaper.
        boolean isSystemBuiltIn = mWallpaperManagerFlag == WallpaperManager.FLAG_SYSTEM
                && !InjectorProvider.getInjector().getWallpaperStatusChecker(context)
                .isHomeStaticWallpaperSet();

        return (isSystemBuiltIn)
                ? new BuiltInWallpaperAsset(context)
                : new CurrentWallpaperAssetVN(context, mWallpaperManagerFlag);
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeStringList(mAttributions);
        parcel.writeInt(mWallpaperManagerFlag);
        parcel.writeString(mActionUrl);
        parcel.writeString(mCollectionId);
        parcel.writeInt(mActionLabelRes);
        parcel.writeInt(mActionIconRes);
    }

    @Override
    public void showPreview(Activity srcActivity, InlinePreviewIntentFactory factory,
                            int requestCode, boolean isAssetIdPresent) {
        srcActivity.startActivityForResult(factory.newIntent(srcActivity, this,
                isAssetIdPresent), requestCode);
    }

    @Override
    public String getStoredWallpaperId(Context context) {
        return null;
    }
}
