package io.zestic.coin.bouncycastle.crypto.tls;

import io.zestic.coin.bouncycastle.crypto.Digest;
import io.zestic.coin.bouncycastle.crypto.digests.MD5Digest;
import io.zestic.coin.bouncycastle.crypto.digests.SHA1Digest;

/**
 * A combined hash, which implements md5(m) || sha1(m).
 */
public class CombinedHash implements Digest {

    private Digest md5 = new MD5Digest();
    private Digest sha1 = new SHA1Digest();

    /**
     * @see Digest#getAlgorithmName()
     */
    public String getAlgorithmName() {
        return md5.getAlgorithmName() + " and " + sha1.getAlgorithmName() + " for TLS 1.0";
    }

    /**
     * @see Digest#getDigestSize()
     */
    public int getDigestSize() {
        return 16 + 20;
    }

    /**
     * @see Digest#update(byte)
     */
    public void update(byte in) {
        md5.update(in);
        sha1.update(in);
    }

    /**
     * @see Digest#update(byte[],int,int)
     */
    public void update(byte[] in, int inOff, int len) {
        md5.update(in, inOff, len);
        sha1.update(in, inOff, len);
    }

    /**
     * @see Digest#doFinal(byte[],int)
     */
    public int doFinal(byte[] out, int outOff) {
        int i1 = md5.doFinal(out, outOff);
        int i2 = sha1.doFinal(out, outOff + 16);
        return i1 + i2;
    }

    /**
     * @see Digest#reset()
     */
    public void reset() {
        md5.reset();
        sha1.reset();
    }

}
