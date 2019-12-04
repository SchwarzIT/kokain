package com.schwarz.kokaindi

import androidx.lifecycle.LifecycleOwner
import java.lang.Exception

object KokainInstance {

    var mInstance: Kokain? = null

    @JvmStatic
    fun start(kokain: Kokain) {
        if (mInstance != null) {
            throw Exception("Kokain already running")
        }
        mInstance = kokain
    }

    fun activityChanged(lifecycleOwner: LifecycleOwner) {
        //TODO
        //lifecycleOwner.lifecycle.addObserver()
    }

    @JvmStatic
    fun stop() = synchronized(this) {
        mInstance?.close()
        mInstance = null
    }
}
