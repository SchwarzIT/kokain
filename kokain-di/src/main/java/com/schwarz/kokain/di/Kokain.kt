package com.schwarz.kokain.di

import android.app.Application
import androidx.activity.ComponentActivity
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.coredi.CustomCurator
import com.schwarz.kokain.coredi.KokainCore

class Kokain(diFactory: KDiFactory, app: Application, customCurator: CustomCurator? = null) : KokainCore(diFactory, customCurator) {

    private val app: Application = app

    var mGuard = ActivityContextGuard(app)

    override fun onBeanResolved(thisRef: Any?, bean: Any?) {
        mGuard?.updateRefererer(thisRef, bean)
    }

    fun refreshActivityContext(activity: ComponentActivity?) {
        activity?.let {
            mGuard.onNewContext(it)
        }
    }

    companion object {
        fun create(diFactory: KDiFactory, app: Application, customCurator: CustomCurator? = null): Kokain {
            return Kokain(diFactory, app, customCurator)
        }
    }
}
