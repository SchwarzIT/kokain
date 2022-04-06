package com.schwarz.kokain.dijvm

import com.schwarz.kokain.api.CustomCurator
import com.schwarz.kokain.api.KDiFactory
import com.schwarz.kokain.corelib.KokainCore

class Kokain(diFactory: KDiFactory, customCurator: CustomCurator? = null) : KokainCore(diFactory, customCurator) {

    companion object {
        fun create(diFactory: KDiFactory, customCurator: CustomCurator? = null): KokainCore {
            return Kokain(diFactory, customCurator)
        }
    }

    override fun onBeanResolved(thisRef: Any?, bean: Any?) {
    }
}
