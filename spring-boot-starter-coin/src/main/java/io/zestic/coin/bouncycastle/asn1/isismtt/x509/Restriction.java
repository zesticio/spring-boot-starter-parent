package io.zestic.coin.bouncycastle.asn1.isismtt.x509;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.DERObject;
import io.zestic.coin.bouncycastle.asn1.DERString;
import io.zestic.coin.bouncycastle.asn1.x500.DirectoryString;

/**
 * Some other restriction regarding the usage of this certificate.
 * RestrictionSyntax ::= DirectoryString (SIZE(1..1024)) */
public class Restriction extends ASN1Encodable {

    private DirectoryString restriction;

    public static Restriction getInstance(Object obj) {
        if (obj == null || obj instanceof Restriction) {
            return (Restriction) obj;
        }

        if (obj instanceof DERString) {
            return new Restriction(DirectoryString.getInstance(obj));
        }

        throw new IllegalArgumentException("illegal object in getInstance: "
                + obj.getClass().getName());
    }

    /**
     * Constructor from DERString.

     * The DERString is of type RestrictionSyntax:

     * RestrictionSyntax ::= DirectoryString (SIZE(1..1024))
     *
     * @param restriction A DERString.
     */
    private Restriction(DirectoryString restriction) {
        this.restriction = restriction;
    }

    /**
     * Constructor from a given details.
     *
     * @param restriction The describtion of the restriction.
     */
    public Restriction(String restriction) {
        this.restriction = new DirectoryString(restriction);
    }

    public DirectoryString getRestriction() {
        return restriction;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.

     * Returns:

     * RestrictionSyntax ::= DirectoryString (SIZE(1..1024))

     *
     * @return a DERObject
     */
    public DERObject toASN1Object() {
        return restriction.toASN1Object();
    }
}
