package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

public class CertStatus extends ASN1Encodable {

    private final ASN1OctetString certHash;
    private final DERInteger certReqId;
    private PKIStatusInfo statusInfo;

    private CertStatus(ASN1Sequence seq) {
        certHash = ASN1OctetString.getInstance(seq.getObjectAt(0));
        certReqId = DERInteger.getInstance(seq.getObjectAt(1));

        if (seq.size() > 2) {
            statusInfo = PKIStatusInfo.getInstance(seq.getObjectAt(2));
        }
    }

    public static CertStatus getInstance(Object o) {
        if (o instanceof CertStatus) {
            return (CertStatus) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CertStatus((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERInteger getCertReqId() {
        return certReqId;
    }

    public PKIStatusInfo getStatusInfo() {
        return statusInfo;
    }

    /**
     * CertStatus ::= SEQUENCE {
     * certHash    OCTET STRING,
     * -- the hash of the certificate, using the same hash algorithm
     * -- as is used to create and verify the certificate signature
     * certReqId   INTEGER,
     * -- to match this confirmation with the corresponding req/rep
     * statusInfo  PKIStatusInfo OPTIONAL
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(certHash);
        v.add(certReqId);

        if (statusInfo != null) {
            v.add(statusInfo);
        }

        return new DERSequence(v);
    }
}
