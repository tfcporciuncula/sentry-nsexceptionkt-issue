package com.fredporciuncula.sentrynsexceptionkt

import com.rickclephas.kmp.nsexceptionkt.sentry.setSentryUnhandledExceptionHook

class SentryInitializer {
  fun init() {
    // Usually we'd have other things before, but the call below is what breaks the build on Sentry 8.7.1+
    setSentryUnhandledExceptionHook()
  }
}
