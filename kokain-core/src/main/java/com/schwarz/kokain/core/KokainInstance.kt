package com.schwarz.kokain.core

import java.lang.Exception

object KokainInstance {

    var mInstance: KokainCore? = null

    @JvmStatic
    fun start(kokainCore: KokainCore) {
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
