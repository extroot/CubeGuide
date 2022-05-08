package ru.extroot.newcubeguide

import android.app.Application

import io.sentry.SentryEvent
import io.sentry.SentryLevel
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid


/**
 * Application class
 */
class CubeGuide: Application() {
    override fun onCreate() {
        super.onCreate()

        // Sentry initialization once for all application
        SentryAndroid.init(this) { options ->
            options.dsn = BuildConfig.SENTRY_DSN
            options.beforeSend =
                SentryOptions.BeforeSendCallback { event: SentryEvent, _: Any? ->
                    if (SentryLevel.DEBUG == event.level) {
                        null
                    } else {
                        event
                    }
                }
        }
    }
}