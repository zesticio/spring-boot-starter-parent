package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

public class CAKeyUpdAnnContent
        extends ASN1Encodable {

    private final CMPCertificate oldWithNew;
    private final CMPCertificate newWithOld;
    private final CMPCertificate newWithNew;

    private CAKeyUpdAnnContent(ASN1Sequence seq) {
        oldWithNew = CMPCertificate.getInstance(seq.getObjectAt(0));
        newWithOld = CMPCertificate.getInstance(seq.getObjectAt(1));
        newWithNew = CMPCertificate.getInstance(seq.getObjectAt(2));
    }

    public static CAKeyUpdAnnContent getInstance(Object o) {
        if (o instanceof CAKeyUpdAnnContent) {
            return (CAKeyUpdAnnContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CAKeyUpdAnnContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CMPCertificate getOldWithNew() {
        return oldWithNew;
    }

    public CMPCertificate getNewWithOld() {
        return newWithOld;
    }

    public CMPCertificate getNewWithNew() {
        return newWithNew;
    }

    /**
     * CAKeyUpdAnnContent ::= SEQUENCE {
     * oldWithNew   CMPCertificate, -- old pub signed with new priv
     * newWithOld   CMPCertificate, -- new pub signed with old priv
     * newWithNew   CMPCertificate  -- new pub signed with new priv
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(oldWithNew);
        v.add(newWithOld);
        v.add(newWithNew);

        return new DERSequence(v);
    }
}
