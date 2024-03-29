package io.zestic.coin.bouncycastle.asn1.crmf;

import io.zestic.coin.bouncycastle.asn1.*;

public class PKIPublicationInfo
        extends ASN1Encodable {

    private final DERInteger action;
    private final ASN1Sequence pubInfos;

    private PKIPublicationInfo(ASN1Sequence seq) {
        action = DERInteger.getInstance(seq.getObjectAt(0));
        pubInfos = ASN1Sequence.getInstance(seq.getObjectAt(1));
    }

    public static PKIPublicationInfo getInstance(Object o) {
        if (o instanceof PKIPublicationInfo) {
            return (PKIPublicationInfo) o;
        }

        if (o instanceof ASN1Sequence) {
            return new PKIPublicationInfo((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public DERInteger getAction() {
        return action;
    }

    public SinglePubInfo[] getPubInfos() {
        if (pubInfos == null) {
            return null;
        }

        SinglePubInfo[] results = new SinglePubInfo[pubInfos.size()];

        for (int i = 0; i != results.length; i++) {
            results[i] = SinglePubInfo.getInstance(pubInfos.getObjectAt(i));
        }

        return results;
    }

    /**
     * PKIPublicationInfo ::= SEQUENCE {
     * action     INTEGER {
     * dontPublish (0),
     * pleasePublish (1) },
     * pubInfos  SEQUENCE SIZE (1..MAX) OF SinglePubInfo OPTIONAL }
     * -- pubInfos MUST NOT be present if action is "dontPublish"
     * -- (if action is "pleasePublish" and pubInfos is omitted,
     * -- "dontCare" is assumed)
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(action);
        v.add(pubInfos);

        return new DERSequence(v);
    }
}
