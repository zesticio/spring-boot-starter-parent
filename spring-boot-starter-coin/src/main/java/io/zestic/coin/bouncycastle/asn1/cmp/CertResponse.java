package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

public class CertResponse extends ASN1Encodable {

    private final DERInteger certReqId;
    private final PKIStatusInfo status;
    private CertifiedKeyPair certifiedKeyPair;
    private ASN1OctetString rspInfo;

    private CertResponse(ASN1Sequence seq) {
        certReqId = DERInteger.getInstance(seq.getObjectAt(0));
        status = PKIStatusInfo.getInstance(seq.getObjectAt(1));

        if (seq.size() >= 3) {
            if (seq.size() == 3) {
                DEREncodable o = seq.getObjectAt(2);
                if (o instanceof ASN1OctetString) {
                    rspInfo = ASN1OctetString.getInstance(o);
                } else {
                    certifiedKeyPair = CertifiedKeyPair.getInstance(o);
                }
            } else {
                certifiedKeyPair = CertifiedKeyPair.getInstance(seq.getObjectAt(2));
                rspInfo = ASN1OctetString.getInstance(seq.getObjectAt(3));
            }
        }
    }

    public static CertResponse getInstance(Object o) {
        if (o instanceof CertResponse) {
            return (CertResponse) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CertResponse((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERInteger getCertReqId() {
        return certReqId;
    }

    public PKIStatusInfo getStatus() {
        return status;
    }

    public CertifiedKeyPair getCertifiedKeyPair() {
        return certifiedKeyPair;
    }

    /**
     * CertResponse ::= SEQUENCE {
     * certReqId           INTEGER,
     * -- to match this response with corresponding request (a value
     * -- of -1 is to be used if certReqId is not specified in the
     * -- corresponding request)
     * status              PKIStatusInfo,
     * certifiedKeyPair    CertifiedKeyPair    OPTIONAL,
     * rspInfo             OCTET STRING        OPTIONAL
     * -- analogous to the id-regInfo-utf8Pairs string defined
     * -- for regInfo in CertReqMsg [CRMF]
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(certReqId);
        v.add(status);

        if (certifiedKeyPair != null) {
            v.add(certifiedKeyPair);
        }

        if (rspInfo != null) {
            v.add(rspInfo);
        }

        return new DERSequence(v);
    }
}
