package io.zestic.coin.bouncycastle.crypto.tls;

import io.zestic.coin.bouncycastle.crypto.digests.SHA1Digest;
import io.zestic.coin.bouncycastle.crypto.signers.DSADigestSigner;
import io.zestic.coin.bouncycastle.crypto.signers.DSASigner;

class TlsDSSSigner
        extends DSADigestSigner {

    TlsDSSSigner() {
        super(new DSASigner(), new SHA1Digest());
    }
}
