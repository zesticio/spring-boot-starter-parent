package in.zestic.common.coin.bouncycastle.crypto.tls;

import in.zestic.common.coin.bouncycastle.crypto.digests.SHA1Digest;
import in.zestic.common.coin.bouncycastle.crypto.signers.DSADigestSigner;
import in.zestic.common.coin.bouncycastle.crypto.signers.DSASigner;

class TlsDSSSigner
        extends DSADigestSigner {

    TlsDSSSigner() {
        super(new DSASigner(), new SHA1Digest());
    }
}
