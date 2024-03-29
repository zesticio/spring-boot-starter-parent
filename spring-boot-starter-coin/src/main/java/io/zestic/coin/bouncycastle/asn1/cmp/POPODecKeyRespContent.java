package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Sequence;
import io.zestic.coin.bouncycastle.asn1.DERInteger;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class POPODecKeyRespContent extends ASN1Encodable {

    private final ASN1Sequence content;

    private POPODecKeyRespContent(ASN1Sequence seq) {
        content = seq;
    }

    public static POPODecKeyRespContent getInstance(Object o) {
        if (o instanceof POPODecKeyRespContent) {
            return (POPODecKeyRespContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new POPODecKeyRespContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERInteger[] toDERIntegerArray() {
        DERInteger[] result = new DERInteger[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = DERInteger.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * POPODecKeyRespContent ::= SEQUENCE OF INTEGER
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
