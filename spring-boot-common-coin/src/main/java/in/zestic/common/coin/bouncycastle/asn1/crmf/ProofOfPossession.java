package in.zestic.common.coin.bouncycastle.asn1.crmf;

import in.zestic.common.coin.bouncycastle.asn1.*;

public class ProofOfPossession
        extends ASN1Encodable
        implements ASN1Choice {

    private final int tagNo;
    private final ASN1Encodable obj;

    private ProofOfPossession(ASN1TaggedObject tagged) {
        tagNo = tagged.getTagNo();
        switch (tagNo) {
            case 0:
                obj = DERNull.INSTANCE;
                break;
            case 1:
                obj = POPOSigningKey.getInstance(tagged, false);
                break;
            case 2:
            case 3:
                obj = POPOPrivKey.getInstance(tagged, false);
                break;
            default:
                throw new IllegalArgumentException("unknown tag: " + tagNo);
        }
    }

    public static ProofOfPossession getInstance(Object o) {
        if (o instanceof ProofOfPossession) {
            return (ProofOfPossession) o;
        }

        if (o instanceof ASN1TaggedObject) {
            return new ProofOfPossession((ASN1TaggedObject) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public int getType() {
        return tagNo;
    }

    public ASN1Encodable getObject() {
        return obj;
    }

    /**
     * ProofOfPossession ::= CHOICE {
     *                           raVerified        [0] NULL,
     *                           -- used if the RA has already verified that the requester is in
     *                           -- possession of the private key
     *                           signature         [1] POPOSigningKey,
     *                           keyEncipherment   [2] POPOPrivKey,
     *                           keyAgreement      [3] POPOPrivKey }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return new DERTaggedObject(false, tagNo, obj);
    }
}
