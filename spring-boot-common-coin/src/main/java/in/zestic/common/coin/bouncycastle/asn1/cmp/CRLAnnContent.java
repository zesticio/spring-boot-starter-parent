package in.zestic.common.coin.bouncycastle.asn1.cmp;

import in.zestic.common.coin.bouncycastle.asn1.ASN1Encodable;
import in.zestic.common.coin.bouncycastle.asn1.ASN1Sequence;
import in.zestic.common.coin.bouncycastle.asn1.DERObject;
import in.zestic.common.coin.bouncycastle.asn1.x509.CertificateList;

public class CRLAnnContent
        extends ASN1Encodable {

    private final ASN1Sequence content;

    private CRLAnnContent(ASN1Sequence seq) {
        content = seq;
    }

    public static CRLAnnContent getInstance(Object o) {
        if (o instanceof CRLAnnContent) {
            return (CRLAnnContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CRLAnnContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CertificateList[] toCertificateListArray() {
        CertificateList[] result = new CertificateList[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = CertificateList.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * CRLAnnContent ::= SEQUENCE OF CertificateList
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
