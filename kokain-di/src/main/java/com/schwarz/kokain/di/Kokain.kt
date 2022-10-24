package com.schwarz.kokain.di

import android.app.Application
import androidx.activity.ComponentActivity
import com.schwarz.kokain.api.CustomCurator
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.corelib.KokainCore

class Kokain(diFactory: KDiFactory, app: Application, customCurator: CustomCurator? = null) :
    KokainCore(diFactory, customCurator) {

    var guard = ActivityContextGuard(app)

    override fun onBeanResolved(thisRef: Any?, bean: Any?) {
        guard.updateRefererer(thisRef, bean)
    }

    fun refreshActivityContext(activity: ComponentActivity?) {
        activity?.let {
            guard.onNewContext(it)
        }
    }

    companion object {
        fun create(
            diFactory: KDiFactory,
            app: Application,
            customCurator: CustomCurator? = null
        ): Kokain {
            return Kokain(diFactory, app, customCurator)
        }
    }
}
