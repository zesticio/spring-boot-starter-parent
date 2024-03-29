package io.zestic.coin.bouncycastle.crypto.params;

import io.zestic.coin.bouncycastle.crypto.CipherParameters;

public class MQVPrivateParameters
        implements CipherParameters {

    private ECPrivateKeyParameters staticPrivateKey;
    private ECPrivateKeyParameters ephemeralPrivateKey;
    private ECPublicKeyParameters ephemeralPublicKey;

    public MQVPrivateParameters(
            ECPrivateKeyParameters staticPrivateKey,
            ECPrivateKeyParameters ephemeralPrivateKey) {
        this(staticPrivateKey, ephemeralPrivateKey, null);
    }

    public MQVPrivateParameters(
            ECPrivateKeyParameters staticPrivateKey,
            ECPrivateKeyParameters ephemeralPrivateKey,
            ECPublicKeyParameters ephemeralPublicKey) {
        this.staticPrivateKey = staticPrivateKey;
        this.ephemeralPrivateKey = ephemeralPrivateKey;
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    public ECPrivateKeyParameters getStaticPrivateKey() {
        return staticPrivateKey;
    }

    public ECPrivateKeyParameters getEphemeralPrivateKey() {
        return ephemeralPrivateKey;
    }

    public ECPublicKeyParameters getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }
}
