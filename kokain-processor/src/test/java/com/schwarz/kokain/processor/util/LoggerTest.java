package com.schwarz.kokain.processor.util;

import com.schwarz.kokain.processor.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

public class LoggerTest {

    private static final String MESSAGE = "message";

    private ProcessingEnvironment mMock;
    private Messager mMessagerMock;

    @Before
    public void init() {
        mMock = Mockito.mock(ProcessingEnvironment.class);

        mMessagerMock = Mockito.mock(Messager.class);
        Mockito.when(mMock.getMessager()).thenReturn(mMessagerMock);
    }

    @Test
    public void testWarn() {

        new Logger(mMock).warn(MESSAGE, null);
        Mockito.verify(mMessagerMock).printMessage(Diagnostic.Kind.WARNING, MESSAGE, null);
    }

    @Test
    public void testError() {

        new Logger(mMock).error(MESSAGE, null);
        Mockito.verify(mMessagerMock).printMessage(Diagnostic.Kind.ERROR, MESSAGE, null);
    }

    @Test
    public void testErrorAbort() {

        Assert.assertThrows(RuntimeException.class, () -> new Logger(mMock).abortWithError(MESSAGE, null, null));
        Mockito.verify(mMessagerMock).printMessage(Diagnostic.Kind.ERROR, MESSAGE, null);

    }

    @Test
    public void testInfo() {

        new Logger(mMock).info(MESSAGE);
        Mockito.verify(mMessagerMock).printMessage(Diagnostic.Kind.NOTE, MESSAGE);

        new Logger(mMock).info(MESSAGE, null);
        Mockito.verify(mMessagerMock).printMessage(Diagnostic.Kind.NOTE, MESSAGE, null);
    }

}
