package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Null;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class PKIConfirmContent
        extends ASN1Encodable {

    private final ASN1Null val;

    private PKIConfirmContent(ASN1Null val) {
        this.val = val;
    }

    public static PKIConfirmContent getInstance(Object o) {
        if (o instanceof PKIConfirmContent) {
            return (PKIConfirmContent) o;
        }

        if (o instanceof ASN1Null) {
            return new PKIConfirmContent((ASN1Null) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    /**
     * PKIConfirmContent ::= NULL
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return val;
    }
}
