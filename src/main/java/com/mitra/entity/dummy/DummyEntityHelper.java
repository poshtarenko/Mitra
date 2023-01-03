package com.mitra.entity.dummy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DummyEntityHelper {

    public static RuntimeException getUnsupportedOperationExceptionAndLog() {
        log.warn("Unsupported method is called in dummy object");
        return new UnsupportedOperationException("Object is dummy");
    }

}
