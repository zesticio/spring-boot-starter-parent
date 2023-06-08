package io.zestic.coin.bouncycastle.asn1.crmf;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.GeneralName;

public class SinglePubInfo
        extends ASN1Encodable {

    private final DERInteger pubMethod;
    private GeneralName pubLocation;

    private SinglePubInfo(ASN1Sequence seq) {
        pubMethod = DERInteger.getInstance(seq.getObjectAt(0));

        if (seq.size() == 2) {
            pubLocation = GeneralName.getInstance(seq.getObjectAt(1));
        }
    }

    public static SinglePubInfo getInstance(Object o) {
        if (o instanceof SinglePubInfo) {
            return (SinglePubInfo) o;
        }

        if (o instanceof ASN1Sequence) {
            return new SinglePubInfo((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public GeneralName getPubLocation() {
        return pubLocation;
    }

    /**
     * SinglePubInfo ::= SEQUENCE {
     * pubMethod    INTEGER {
     * dontCare    (0),
     * x500        (1),
     * web         (2),
     * ldap        (3) },
     * pubLocation  GeneralName OPTIONAL }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(pubMethod);

        if (pubLocation != null) {
            v.add(pubLocation);
        }

        return new DERSequence(v);
    }
}
