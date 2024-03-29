package io.zestic.coin.bouncycastle.bcpg.sig;

import io.zestic.coin.bouncycastle.bcpg.SignatureSubpacket;
import io.zestic.coin.bouncycastle.bcpg.SignatureSubpacketTags;

import java.util.Date;

/**
 * packet giving signature creation time.
 */
public class SignatureCreationTime
        extends SignatureSubpacket {

    protected static byte[] timeToBytes(
            Date date) {
        byte[] data = new byte[4];
        long t = date.getTime() / 1000;

        data[0] = (byte) (t >> 24);
        data[1] = (byte) (t >> 16);
        data[2] = (byte) (t >> 8);
        data[3] = (byte) t;

        return data;
    }

    public SignatureCreationTime(
            boolean critical,
            byte[] data) {
        super(SignatureSubpacketTags.CREATION_TIME, critical, data);
    }

    public SignatureCreationTime(
            boolean critical,
            Date date) {
        super(SignatureSubpacketTags.CREATION_TIME, critical, timeToBytes(date));
    }

    public Date getTime() {
        long time = ((long) (data[0] & 0xff) << 24) | ((data[1] & 0xff) << 16) | ((data[2] & 0xff) << 8) | (data[3] & 0xff);

        return new Date(time * 1000);
    }
}
