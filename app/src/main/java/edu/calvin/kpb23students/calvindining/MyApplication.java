package edu.calvin.kpb23students.calvindining;

import android.app.Application;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * <p>
 * This gets information from the server
 * <p/>
 *
 * @author Kristofer
 * @version Fall, 2016
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    private Retrofit retrofit;
    private CalvinDiningService calvinDiningService;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        String BASE_URL = "http://sam.ohnopub.net/~kpgbrink/CalvinDiningServer/server.cgi/";

        // http://stackoverflow.com/a/23503804/2948122
        File httpCacheDirectory = new File(getApplicationContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder()
                    .cache(new Cache(httpCacheDirectory, cacheSize))
                    .build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        calvinDiningService = new CalvinDiningService(retrofit);
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public CalvinDiningService getCalvinDiningService() {
        return calvinDiningService;
    }

}