package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

public class PollRepContent
        extends ASN1Encodable {

    private final DERInteger certReqId;
    private final DERInteger checkAfter;
    private PKIFreeText reason;

    private PollRepContent(ASN1Sequence seq) {
        certReqId = DERInteger.getInstance(seq.getObjectAt(0));
        checkAfter = DERInteger.getInstance(seq.getObjectAt(1));

        if (seq.size() > 2) {
            reason = PKIFreeText.getInstance(seq.getObjectAt(2));
        }
    }

    public static PollRepContent getInstance(Object o) {
        if (o instanceof PollRepContent) {
            return (PollRepContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new PollRepContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERInteger getCertReqId() {
        return certReqId;
    }

    public DERInteger getCheckAfter() {
        return checkAfter;
    }

    public PKIFreeText getReason() {
        return reason;
    }

    /**
     * PollRepContent ::= SEQUENCE OF SEQUENCE {
     * certReqId              INTEGER,
     * checkAfter             INTEGER,  -- time in seconds
     * reason                 PKIFreeText OPTIONAL
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(certReqId);
        v.add(checkAfter);

        if (reason != null) {
            v.add(reason);
        }

        return new DERSequence(v);
    }
}
