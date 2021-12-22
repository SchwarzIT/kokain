package com.example.demolibrary

import com.schwarz.kokain.api.EBean
import com.schwarz.kokain.di.inject

@EBean
open class LibraryFoBean {

    private val internalBean: InternalBean by inject()

    fun doSomething() {
    }
}
