package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Sequence;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class RevReqContent
        extends ASN1Encodable {

    private final ASN1Sequence content;

    private RevReqContent(ASN1Sequence seq) {
        content = seq;
    }

    public static RevReqContent getInstance(Object o) {
        if (o instanceof RevReqContent) {
            return (RevReqContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new RevReqContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public RevDetails[] toRevDetailsArray() {
        RevDetails[] result = new RevDetails[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = RevDetails.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * RevReqContent ::= SEQUENCE OF RevDetails
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
