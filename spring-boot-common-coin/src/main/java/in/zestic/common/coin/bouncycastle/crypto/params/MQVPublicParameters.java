package in.zestic.common.coin.bouncycastle.crypto.params;

import in.zestic.common.coin.bouncycastle.crypto.CipherParameters;

public class MQVPublicParameters
        implements CipherParameters {

    private ECPublicKeyParameters staticPublicKey;
    private ECPublicKeyParameters ephemeralPublicKey;

    public MQVPublicParameters(
            ECPublicKeyParameters staticPublicKey,
            ECPublicKeyParameters ephemeralPublicKey) {
        this.staticPublicKey = staticPublicKey;
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    public ECPublicKeyParameters getStaticPublicKey() {
        return staticPublicKey;
    }

    public ECPublicKeyParameters getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }
}
