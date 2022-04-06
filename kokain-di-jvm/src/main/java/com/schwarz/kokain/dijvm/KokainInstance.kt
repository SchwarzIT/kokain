package com.schwarz.kokain.dijvm

import java.lang.Exception

object KokainInstance {

    var mInstance: Kokain? = null

    @JvmStatic
    fun start(kokainCore: Kokain) {
        if (mInstance != null) {
            throw Exception("Kokain already running")
        }
        mInstance = kokainCore
    }

    @JvmStatic
    fun stop() = synchronized(this) {
        mInstance?.close()
        mInstance = null
    }
}
