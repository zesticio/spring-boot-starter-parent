package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.crmf.CertId;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash
        extends ASN1Encodable {

    private final DERBitString hashVal;
    private AlgorithmIdentifier hashAlg;
    private CertId certId;

    private OOBCertHash(ASN1Sequence seq) {
        int index = seq.size() - 1;

        hashVal = DERBitString.getInstance(seq.getObjectAt(index--));

        for (int i = index; i >= 0; i--) {
            ASN1TaggedObject tObj = (ASN1TaggedObject) seq.getObjectAt(i);

            if (tObj.getTagNo() == 0) {
                hashAlg = AlgorithmIdentifier.getInstance(tObj, true);
            } else {
                certId = CertId.getInstance(tObj, true);
            }
        }

    }

    public static OOBCertHash getInstance(Object o) {
        if (o instanceof OOBCertHash) {
            return (OOBCertHash) o;
        }

        if (o instanceof ASN1Sequence) {
            return new OOBCertHash((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public AlgorithmIdentifier getHashAlg() {
        return hashAlg;
    }

    public CertId getCertId() {
        return certId;
    }

    /**
     * OOBCertHash ::= SEQUENCE {
     * hashAlg     [0] AlgorithmIdentifier     OPTIONAL,
     * certId      [1] CertId                  OPTIONAL,
     * hashVal         BIT STRING
     * -- hashVal is calculated over the DER encoding of the
     * -- self-signed certificate with the identifier certID.
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        addOptional(v, 0, hashAlg);
        addOptional(v, 1, certId);

        v.add(hashVal);

        return new DERSequence(v);
    }

    private void addOptional(ASN1EncodableVector v, int tagNo, ASN1Encodable obj) {
        if (obj != null) {
            v.add(new DERTaggedObject(true, tagNo, obj));
        }
    }
}
