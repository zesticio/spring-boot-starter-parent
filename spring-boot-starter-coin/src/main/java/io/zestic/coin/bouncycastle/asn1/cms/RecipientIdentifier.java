package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;

public class RecipientIdentifier
        extends ASN1Encodable
        implements ASN1Choice {

    private final DEREncodable id;

    public RecipientIdentifier(
            IssuerAndSerialNumber id) {
        this.id = id;
    }

    public RecipientIdentifier(
            ASN1OctetString id) {
        this.id = new DERTaggedObject(false, 0, id);
    }

    public RecipientIdentifier(
            DERObject id) {
        this.id = id;
    }

    /**
     * return a RecipientIdentifier object from the given object.
     *
     * @param o the object we want converted.
     * @throws IllegalArgumentException if the object cannot be converted.
     */
    public static RecipientIdentifier getInstance(
            Object o) {
        if (o == null || o instanceof RecipientIdentifier) {
            return (RecipientIdentifier) o;
        }

        if (o instanceof IssuerAndSerialNumber) {
            return new RecipientIdentifier((IssuerAndSerialNumber) o);
        }

        if (o instanceof ASN1OctetString) {
            return new RecipientIdentifier((ASN1OctetString) o);
        }

        if (o instanceof DERObject) {
            return new RecipientIdentifier((DERObject) o);
        }

        throw new IllegalArgumentException(
                "Illegal object in RecipientIdentifier: " + o.getClass().getName());
    }

    public boolean isTagged() {
        return (id instanceof ASN1TaggedObject);
    }

    public DEREncodable getId() {
        if (id instanceof ASN1TaggedObject) {
            return ASN1OctetString.getInstance((ASN1TaggedObject) id, false);
        }

        return IssuerAndSerialNumber.getInstance(id);
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * RecipientIdentifier ::= CHOICE {
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
