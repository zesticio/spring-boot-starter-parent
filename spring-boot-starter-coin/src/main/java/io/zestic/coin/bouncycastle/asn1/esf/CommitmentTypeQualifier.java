package io.zestic.coin.bouncycastle.asn1.esf;

import io.zestic.coin.bouncycastle.asn1.*;

/**
 * Commitment type qualifiers, used in the Commitment-Type-Indication attribute
 * (RFC3126).
 * <p>
 * CommitmentTypeQualifier ::= SEQUENCE {
 * commitmentTypeIdentifier  CommitmentTypeIdentifier,
 * qualifier          ANY DEFINED BY commitmentTypeIdentifier OPTIONAL }
 */
public class CommitmentTypeQualifier
        extends ASN1Encodable {

    private final DERObjectIdentifier commitmentTypeIdentifier;
    private DEREncodable qualifier;

    /**
     * Creates a new <code>CommitmentTypeQualifier</code> instance.
     *
     * @param commitmentTypeIdentifier a <code>CommitmentTypeIdentifier</code>
     *                                 value
     */
    public CommitmentTypeQualifier(
            DERObjectIdentifier commitmentTypeIdentifier) {
        this(commitmentTypeIdentifier, null);
    }

    /**
     * Creates a new <code>CommitmentTypeQualifier</code> instance.
     *
     * @param commitmentTypeIdentifier a <code>CommitmentTypeIdentifier</code>
     *                                 value
     * @param qualifier                the qualifier, defined by the above field.
     */
    public CommitmentTypeQualifier(
            DERObjectIdentifier commitmentTypeIdentifier,
            DEREncodable qualifier) {
        this.commitmentTypeIdentifier = commitmentTypeIdentifier;
        this.qualifier = qualifier;
    }

    /**
     * Creates a new <code>CommitmentTypeQualifier</code> instance.
     *
     * @param as <code>CommitmentTypeQualifier</code> structure encoded as an
     *           ASN1Sequence.
     */
    public CommitmentTypeQualifier(
            ASN1Sequence as) {
        commitmentTypeIdentifier = (DERObjectIdentifier) as.getObjectAt(0);

        if (as.size() > 1) {
            qualifier = as.getObjectAt(1);
        }
    }

    public static CommitmentTypeQualifier getInstance(Object as) {
        if (as instanceof CommitmentTypeQualifier || as == null) {
            return (CommitmentTypeQualifier) as;
        } else if (as instanceof ASN1Sequence) {
            return new CommitmentTypeQualifier((ASN1Sequence) as);
        }

        throw new IllegalArgumentException("unknown object in getInstance.");
    }

    public DERObjectIdentifier getCommitmentTypeIdentifier() {
        return commitmentTypeIdentifier;
    }

    public DEREncodable getQualifier() {
        return qualifier;
    }

    /**
     * Returns a DER-encodable representation of this instance.
     *
     * @return a <code>DERObject</code> value
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector dev = new ASN1EncodableVector();
        dev.add(commitmentTypeIdentifier);
        if (qualifier != null) {
            dev.add(qualifier);
        }

        return new DERSequence(dev);
    }
}
