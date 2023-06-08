package io.zestic.coin.bouncycastle.bcpg.sig;

import io.zestic.coin.bouncycastle.bcpg.SignatureSubpacket;
import io.zestic.coin.bouncycastle.bcpg.SignatureSubpacketTags;

/**
 * Packet embedded signature
 */
public class EmbeddedSignature
        extends SignatureSubpacket {

    public EmbeddedSignature(
            boolean critical,
            byte[] data) {
        super(SignatureSubpacketTags.EMBEDDED_SIGNATURE, critical, data);
    }
}
