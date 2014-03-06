package com.evastos.dogeweather.application;

import android.app.Application;

import com.evastos.dogeweather.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 Copyright 2011-2014 Sergey Tarasevich

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 **/

/**
 * The Class DogeWeatherApp.
 *
 * @author Eva
 */
public class DogeWeatherApp extends Application {

    /* API key for WorldWeatherOnline */
    private static final String WORLD_WEATHER_ONLINE_API_KEY = "";

    public static String getWorldWeatherOnlineApiKey() {
        return WORLD_WEATHER_ONLINE_API_KEY;
    }

    @Override

    public void onCreate() {
        super.onCreate();

        /* Intialise image loader */
        setupImageLoader();
    }

    private void setupImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .showImageOnFail(R.color.light_gray)
                .showImageOnFail(R.layout.progress_bar_loading)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(5)
                .defaultDisplayImageOptions(options)
                .discCacheFileCount(100)
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

}
