package io.zestic.coin.bouncycastle.asn1.crmf;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.Time;

import java.util.Enumeration;

public class OptionalValidity
        extends ASN1Encodable {

    private Time notBefore;
    private Time notAfter;

    private OptionalValidity(ASN1Sequence seq) {
        Enumeration en = seq.getObjects();
        while (en.hasMoreElements()) {
            ASN1TaggedObject tObj = (ASN1TaggedObject) en.nextElement();

            if (tObj.getTagNo() == 0) {
                notBefore = Time.getInstance(tObj, true);
            } else {
                notAfter = Time.getInstance(tObj, true);
            }
        }
    }

    public static OptionalValidity getInstance(Object o) {
        if (o instanceof OptionalValidity) {
            return (OptionalValidity) o;
        }

        if (o instanceof ASN1Sequence) {
            return new OptionalValidity((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    /**
     * OptionalValidity ::= SEQUENCE {
     * notBefore  [0] Time OPTIONAL,
     * notAfter   [1] Time OPTIONAL } --at least one MUST be present
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        if (notBefore != null) {
            v.add(new DERTaggedObject(true, 0, notBefore));
        }

        if (notAfter != null) {
            v.add(new DERTaggedObject(true, 1, notAfter));
        }

        return new DERSequence(v);
    }
}
