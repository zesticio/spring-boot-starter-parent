package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;

public class RecipientEncryptedKey
        extends ASN1Encodable {

    private final KeyAgreeRecipientIdentifier identifier;
    private final ASN1OctetString encryptedKey;

    private RecipientEncryptedKey(
            ASN1Sequence seq) {
        identifier = KeyAgreeRecipientIdentifier.getInstance(seq.getObjectAt(0));
        encryptedKey = (ASN1OctetString) seq.getObjectAt(1);
    }

    public RecipientEncryptedKey(
            KeyAgreeRecipientIdentifier id,
            ASN1OctetString encryptedKey) {
        this.identifier = id;
        this.encryptedKey = encryptedKey;
    }

    /**
     * return an RecipientEncryptedKey object from a tagged object.
     *
     * @param obj      the tagged object holding the object we want.
     * @param explicit true if the object is meant to be explicitly tagged false
     *                 otherwise.
     * @throws IllegalArgumentException if the object held by the tagged
     *                                  object cannot be converted.
     */
    public static RecipientEncryptedKey getInstance(
            ASN1TaggedObject obj,
            boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    /**
     * return a RecipientEncryptedKey object from the given object.
     *
     * @param obj the object we want converted.
     * @throws IllegalArgumentException if the object cannot be converted.
     */
    public static RecipientEncryptedKey getInstance(
            Object obj) {
        if (obj == null || obj instanceof RecipientEncryptedKey) {
            return (RecipientEncryptedKey) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new RecipientEncryptedKey((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("Invalid RecipientEncryptedKey: " + obj.getClass().getName());
    }

    public KeyAgreeRecipientIdentifier getIdentifier() {
        return identifier;
    }

    public ASN1OctetString getEncryptedKey() {
        return encryptedKey;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * RecipientEncryptedKey ::= SEQUENCE {
     * rid KeyAgreeRecipientIdentifier,
     * encryptedKey EncryptedKey
     * }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(identifier);
        v.add(encryptedKey);

        return new DERSequence(v);
    }
}
