package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OriginatorPublicKey
        extends ASN1Encodable {

    private final AlgorithmIdentifier algorithm;
    private final DERBitString publicKey;

    public OriginatorPublicKey(
            AlgorithmIdentifier algorithm,
            byte[] publicKey) {
        this.algorithm = algorithm;
        this.publicKey = new DERBitString(publicKey);
    }

    public OriginatorPublicKey(
            ASN1Sequence seq) {
        algorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
        publicKey = (DERBitString) seq.getObjectAt(1);
    }

    /**
     * return an OriginatorPublicKey object from a tagged object.
     *
     * @param obj      the tagged object holding the object we want.
     * @param explicit true if the object is meant to be explicitly tagged false
     *                 otherwise.
     * @throws IllegalArgumentException if the object held by the tagged
     *                                  object cannot be converted.
     */
    public static OriginatorPublicKey getInstance(
            ASN1TaggedObject obj,
            boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    /**
     * return an OriginatorPublicKey object from the given object.
     *
     * @param obj the object we want converted.
     * @throws IllegalArgumentException if the object cannot be converted.
     */
    public static OriginatorPublicKey getInstance(
            Object obj) {
        if (obj == null || obj instanceof OriginatorPublicKey) {
            return (OriginatorPublicKey) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new OriginatorPublicKey((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("Invalid OriginatorPublicKey: " + obj.getClass().getName());
    }

    public AlgorithmIdentifier getAlgorithm() {
        return algorithm;
    }

    public DERBitString getPublicKey() {
        return publicKey;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * OriginatorPublicKey ::= SEQUENCE {
     * algorithm AlgorithmIdentifier,
     * publicKey BIT STRING
     * }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(algorithm);
        v.add(publicKey);

        return new DERSequence(v);
    }
}
