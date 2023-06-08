package io.zestic.coin.bouncycastle.asn1.ocsp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1EncodableVector;
import io.zestic.coin.bouncycastle.asn1.DERObject;
import io.zestic.coin.bouncycastle.asn1.DERSequence;
import io.zestic.coin.bouncycastle.asn1.x509.X509Name;

public class ServiceLocator
        extends ASN1Encodable {

    X509Name issuer;
    DERObject locator;

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * ServiceLocator ::= SEQUENCE {
     * issuer    Name,
     * locator   AuthorityInfoAccessSyntax OPTIONAL }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(issuer);

        if (locator != null) {
            v.add(locator);
        }

        return new DERSequence(v);
    }
}
