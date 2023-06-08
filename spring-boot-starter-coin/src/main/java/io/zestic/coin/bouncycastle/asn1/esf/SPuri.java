package io.zestic.coin.bouncycastle.asn1.esf;

import io.zestic.coin.bouncycastle.asn1.DERIA5String;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class SPuri {

    private final DERIA5String uri;

    public SPuri(
            DERIA5String uri) {
        this.uri = uri;
    }

    public static SPuri getInstance(
            Object obj) {
        if (obj instanceof SPuri) {
            return (SPuri) obj;
        } else if (obj instanceof DERIA5String) {
            return new SPuri((DERIA5String) obj);
        }

        throw new IllegalArgumentException(
                "unknown object in 'SPuri' factory: "
                        + obj.getClass().getName() + ".");
    }

    public DERIA5String getUri() {
        return uri;
    }

    /**
     * SPuri ::= IA5String
     */
    public DERObject toASN1Object() {
        return uri.getDERObject();
    }
}
