package io.zestic.coin.bouncycastle.crypto.io;

import io.zestic.coin.bouncycastle.crypto.Signer;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SignerOutputStream
        extends FilterOutputStream {

    protected Signer signer;

    public SignerOutputStream(
            OutputStream stream,
            Signer signer) {
        super(stream);
        this.signer = signer;
    }

    public void write(int b)
            throws IOException {
        signer.update((byte) b);
        out.write(b);
    }

    public void write(
            byte[] b,
            int off,
            int len)
            throws IOException {
        signer.update(b, off, len);
        out.write(b, off, len);
    }

    public Signer getSigner() {
        return signer;
    }
}
