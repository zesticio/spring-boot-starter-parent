package io.zestic.coin.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * base class for a DSA Secret Key.
 */
public class DSASecretBCPGKey
        extends BCPGObject implements BCPGKey {

    MPInteger x;

    /**
     *
     * @param in
     * @throws IOException
     */
    public DSASecretBCPGKey(
            BCPGInputStream in)
            throws IOException {
        this.x = new MPInteger(in);
    }

    /**
     *
     * @param x
     */
    public DSASecretBCPGKey(
            BigInteger x) {
        this.x = new MPInteger(x);
    }

    /**
     * return "PGP"
     *
     * @see BCPGKey#getFormat()
     */
    public String getFormat() {
        return "PGP";
    }

    /**
     * return the standard PGP encoding of the key.
     *
     * @see BCPGKey#getEncoded()
     */
    public byte[] getEncoded() {
        try {
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            BCPGOutputStream pgpOut = new BCPGOutputStream(bOut);

            pgpOut.writeObject(this);

            return bOut.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public void encode(
            BCPGOutputStream out)
            throws IOException {
        out.writeObject(x);
    }

    /**
     * @return x
     */
    public BigInteger getX() {
        return x.getValue();
    }
}
