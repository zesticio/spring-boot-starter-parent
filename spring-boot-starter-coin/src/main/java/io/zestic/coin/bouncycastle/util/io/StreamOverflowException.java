package io.zestic.coin.bouncycastle.util.io;

import java.io.IOException;

public class StreamOverflowException
        extends IOException {

    public StreamOverflowException(String msg) {
        super(msg);
    }
}
