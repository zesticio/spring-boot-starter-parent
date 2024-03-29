package io.zestic.coin.bouncycastle.asn1.crmf;

import io.zestic.coin.bouncycastle.asn1.*;

public class AttributeTypeAndValue
        extends ASN1Encodable {

    private final DERObjectIdentifier type;
    private final ASN1Encodable value;

    private AttributeTypeAndValue(ASN1Sequence seq) {
        type = (DERObjectIdentifier) seq.getObjectAt(0);
        value = (ASN1Encodable) seq.getObjectAt(1);
    }

    public static AttributeTypeAndValue getInstance(Object o) {
        if (o instanceof AttributeTypeAndValue) {
            return (AttributeTypeAndValue) o;
        }

        if (o instanceof ASN1Sequence) {
            return new AttributeTypeAndValue((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERObjectIdentifier getType() {
        return type;
    }

    public ASN1Encodable getValue() {
        return value;
    }

    /**
     * AttributeTypeAndValue ::= SEQUENCE {
     * type         OBJECT IDENTIFIER,
     * value        ANY DEFINED BY type }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(type);
        v.add(value);

        return new DERSequence(v);
    }
}
