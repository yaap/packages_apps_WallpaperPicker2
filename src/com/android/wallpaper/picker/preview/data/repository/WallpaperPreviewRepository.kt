/*
 * Copyright 2023 The Android Open Source Project
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

package com.android.wallpaper.picker.preview.data.repository

import com.android.wallpaper.model.wallpaper.WallpaperModel
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** This repository class manages the [WallpaperModel] for the preview screen */
@ActivityRetainedScoped
class WallpaperPreviewRepository @Inject constructor() {
    /** This [WallpaperModel] represents the current selected wallpaper */
    private val _wallpaperModel = MutableStateFlow<WallpaperModel?>(null)
    val wallpaperModel: StateFlow<WallpaperModel?> = _wallpaperModel.asStateFlow()

    fun setWallpaperModel(updatedWallpaperModel: WallpaperModel?) {
        _wallpaperModel.update { updatedWallpaperModel }
    }
}
