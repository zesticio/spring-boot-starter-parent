package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;

public class EncryptedData
        extends ASN1Encodable {

    private final DERInteger version;
    private final EncryptedContentInfo encryptedContentInfo;
    private ASN1Set unprotectedAttrs;

    public EncryptedData(EncryptedContentInfo encInfo) {
        this(encInfo, null);
    }

    public EncryptedData(EncryptedContentInfo encInfo, ASN1Set unprotectedAttrs) {
        this.version = new DERInteger((unprotectedAttrs == null) ? 0 : 2);
        this.encryptedContentInfo = encInfo;
        this.unprotectedAttrs = unprotectedAttrs;
    }

    private EncryptedData(ASN1Sequence seq) {
        this.version = DERInteger.getInstance(seq.getObjectAt(0));
        this.encryptedContentInfo = EncryptedContentInfo.getInstance(seq.getObjectAt(1));

        if (seq.size() == 3) {
            this.unprotectedAttrs = ASN1Set.getInstance(seq.getObjectAt(2));
        }
    }

    public static EncryptedData getInstance(Object o) {
        if (o instanceof EncryptedData) {
            return (EncryptedData) o;
        }

        if (o instanceof ASN1Sequence) {
            return new EncryptedData((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid EncryptedData: " + o.getClass().getName());
    }

    public DERInteger getVersion() {
        return version;
    }

    public EncryptedContentInfo getEncryptedContentInfo() {
        return encryptedContentInfo;
    }

    public ASN1Set getUnprotectedAttrs() {
        return unprotectedAttrs;
    }

    /**
     * EncryptedData ::= SEQUENCE {
     * version CMSVersion,
     * encryptedContentInfo EncryptedContentInfo,
     * unprotectedAttrs [1] IMPLICIT UnprotectedAttributes OPTIONAL }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(version);
        v.add(encryptedContentInfo);
        if (unprotectedAttrs != null) {
            v.add(new BERTaggedObject(false, 1, unprotectedAttrs));
        }

        return new BERSequence(v);
    }
}
