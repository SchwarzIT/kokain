package com.schwarz.kokain.processor

import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.tools.Diagnostic

class Logger internal constructor(processingEnv: ProcessingEnvironment) {

    private val messager: Messager

    private var hasErrors: Boolean = false

    init {
        this.messager = processingEnv.messager
    }

    fun info(msg: String) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg)
    }

    fun info(msg: String, e: Element?) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg, e)
    }

    fun warn(msg: String, e: Element?) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg, e)
    }

    fun error(msg: String, e: Element?) {
        hasErrors = true
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e)
    }

    fun error(msg: String) {
        hasErrors = true
        messager.printMessage(Diagnostic.Kind.ERROR, msg)
    }

    fun abortWithError(msg: String, e: Element?, ex: Throwable?) {
        error(msg, e)
        throw RuntimeException(ex)
    }

    fun hasErrors(): Boolean {
        return hasErrors
    }
}
