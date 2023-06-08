package io.zestic.coin.bouncycastle.crypto.params;

import io.zestic.coin.bouncycastle.math.ec.ECPoint;

public class ECPublicKeyParameters
        extends ECKeyParameters {

    ECPoint Q;

    public ECPublicKeyParameters(
            ECPoint Q,
            ECDomainParameters params) {
        super(false, params);
        this.Q = Q;
    }

    public ECPoint getQ() {
        return Q;
    }
}
