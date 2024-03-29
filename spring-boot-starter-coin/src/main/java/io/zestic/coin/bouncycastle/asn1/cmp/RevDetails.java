package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.crmf.CertTemplate;
import io.zestic.coin.bouncycastle.asn1.x509.X509Extensions;

public class RevDetails
        extends ASN1Encodable {

    private final CertTemplate certDetails;
    private X509Extensions crlEntryDetails;

    private RevDetails(ASN1Sequence seq) {
        certDetails = CertTemplate.getInstance(seq.getObjectAt(0));
        if (seq.size() > 1) {
            crlEntryDetails = X509Extensions.getInstance(seq.getObjectAt(1));
        }
    }

    public static RevDetails getInstance(Object o) {
        if (o instanceof RevDetails) {
            return (RevDetails) o;
        }

        if (o instanceof ASN1Sequence) {
            return new RevDetails((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CertTemplate getCertDetails() {
        return certDetails;
    }

    public X509Extensions getCrlEntryDetails() {
        return crlEntryDetails;
    }

    /**
     * RevDetails ::= SEQUENCE {
     * certDetails         CertTemplate,
     * -- allows requester to specify as much as they can about
     * -- the cert. for which revocation is requested
     * -- (e.g., for cases in which serialNumber is not available)
     * crlEntryDetails     Extensions       OPTIONAL
     * -- requested crlEntryExtensions
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(certDetails);

        if (crlEntryDetails != null) {
            v.add(crlEntryDetails);
        }

        return new DERSequence(v);
    }
}
