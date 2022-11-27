package com.mitra.entity.dummy;

public final class DummyHelper {

    public static RuntimeException getUnsupportedOperationExceptionAndLog() {
        // TODO : log
        return new UnsupportedOperationException("Object is dummy");
    }

}
