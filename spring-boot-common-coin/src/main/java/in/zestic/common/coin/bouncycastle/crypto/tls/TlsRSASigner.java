package in.zestic.common.coin.bouncycastle.crypto.tls;

import in.zestic.common.coin.bouncycastle.crypto.encodings.PKCS1Encoding;
import in.zestic.common.coin.bouncycastle.crypto.engines.RSABlindedEngine;
import in.zestic.common.coin.bouncycastle.crypto.signers.GenericSigner;

class TlsRSASigner
        extends GenericSigner {

    TlsRSASigner() {
        super(new PKCS1Encoding(new RSABlindedEngine()), new CombinedHash());
    }
}
