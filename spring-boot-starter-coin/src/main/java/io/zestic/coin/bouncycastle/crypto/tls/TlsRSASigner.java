package io.zestic.coin.bouncycastle.crypto.tls;

import io.zestic.coin.bouncycastle.crypto.encodings.PKCS1Encoding;
import io.zestic.coin.bouncycastle.crypto.engines.RSABlindedEngine;
import io.zestic.coin.bouncycastle.crypto.signers.GenericSigner;

class TlsRSASigner
        extends GenericSigner {

    TlsRSASigner() {
        super(new PKCS1Encoding(new RSABlindedEngine()), new CombinedHash());
    }
}
