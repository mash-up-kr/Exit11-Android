package io.mashup.exit11.data.remote.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public class NetworkErrorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
