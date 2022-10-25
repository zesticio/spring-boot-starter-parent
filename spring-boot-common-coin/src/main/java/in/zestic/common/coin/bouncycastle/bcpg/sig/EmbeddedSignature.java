package in.zestic.common.coin.bouncycastle.bcpg.sig;

import in.zestic.common.coin.bouncycastle.bcpg.SignatureSubpacket;
import in.zestic.common.coin.bouncycastle.bcpg.SignatureSubpacketTags;

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
