package io.zestic.coin.bouncycastle.asn1.esf;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OtherHashAlgAndValue
        extends ASN1Encodable {

    private final AlgorithmIdentifier hashAlgorithm;
    private final ASN1OctetString hashValue;

    public OtherHashAlgAndValue(
            ASN1Sequence seq) {
        if (seq.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }

        hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
        hashValue = ASN1OctetString.getInstance(seq.getObjectAt(1));
    }

    public OtherHashAlgAndValue(
            AlgorithmIdentifier hashAlgorithm,
            ASN1OctetString hashValue) {
        this.hashAlgorithm = hashAlgorithm;
        this.hashValue = hashValue;
    }

    public static OtherHashAlgAndValue getInstance(
            Object obj) {
        if (obj == null || obj instanceof OtherHashAlgAndValue) {
            return (OtherHashAlgAndValue) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new OtherHashAlgAndValue((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException(
                "unknown object in 'OtherHashAlgAndValue' factory : "
                        + obj.getClass().getName() + ".");
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return hashAlgorithm;
    }

    public ASN1OctetString getHashValue() {
        return hashValue;
    }

    /**
     * OtherHashAlgAndValue ::= SEQUENCE {
     * hashAlgorithm AlgorithmIdentifier,
     * hashValue OtherHashValue }
     * <p>
     * OtherHashValue ::= OCTET STRING
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(hashAlgorithm);
        v.add(hashValue);

        return new DERSequence(v);
    }
}
