package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;

public class SignerIdentifier
        extends ASN1Encodable
        implements ASN1Choice {

    private final DEREncodable id;

    public SignerIdentifier(
            IssuerAndSerialNumber id) {
        this.id = id;
    }

    public SignerIdentifier(
            ASN1OctetString id) {
        this.id = new DERTaggedObject(false, 0, id);
    }

    public SignerIdentifier(
            DERObject id) {
        this.id = id;
    }

    /**
     * return a SignerIdentifier object from the given object.
     *
     * @param o the object we want converted.
     * @throws IllegalArgumentException if the object cannot be converted.
     */
    public static SignerIdentifier getInstance(
            Object o) {
        if (o == null || o instanceof SignerIdentifier) {
            return (SignerIdentifier) o;
        }

        if (o instanceof IssuerAndSerialNumber) {
            return new SignerIdentifier((IssuerAndSerialNumber) o);
        }

        if (o instanceof ASN1OctetString) {
            return new SignerIdentifier((ASN1OctetString) o);
        }

        if (o instanceof DERObject) {
            return new SignerIdentifier((DERObject) o);
        }

        throw new IllegalArgumentException(
                "Illegal object in SignerIdentifier: " + o.getClass().getName());
    }

    public boolean isTagged() {
        return (id instanceof ASN1TaggedObject);
    }

    public DEREncodable getId() {
        if (id instanceof ASN1TaggedObject) {
            return ASN1OctetString.getInstance((ASN1TaggedObject) id, false);
        }

        return id;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * SignerIdentifier ::= CHOICE {
     * issuerAndSerialNumber IssuerAndSerialNumber,
     * subjectKeyIdentifier [0] SubjectKeyIdentifier
     * }
     * <p>
     * SubjectKeyIdentifier ::= OCTET STRING
     */
    public DERObject toASN1Object() {
        return id.getDERObject();
    }
}
