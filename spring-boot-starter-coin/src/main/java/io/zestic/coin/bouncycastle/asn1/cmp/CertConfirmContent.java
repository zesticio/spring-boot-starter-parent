package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Sequence;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class CertConfirmContent
        extends ASN1Encodable {

    private final ASN1Sequence content;

    private CertConfirmContent(ASN1Sequence seq) {
        content = seq;
    }

    public static CertConfirmContent getInstance(Object o) {
        if (o instanceof CertConfirmContent) {
            return (CertConfirmContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CertConfirmContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CertStatus[] toCertStatusArray() {
        CertStatus[] result = new CertStatus[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = CertStatus.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * CertConfirmContent ::= SEQUENCE OF CertStatus
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
