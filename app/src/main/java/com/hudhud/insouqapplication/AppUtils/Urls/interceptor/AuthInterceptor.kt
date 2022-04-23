package com.hudhud.insouqapplication.AppUtils.Urls.interceptor

import com.hudhud.insouqapplication.AppUtils.AppDefs.AppDefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (AppDefs.user.token.isNullOrEmpty()) {
            chain.proceed(chain.request())
        } else {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${AppDefs.user.token}")
                    .build()
            )
        }
    }

}