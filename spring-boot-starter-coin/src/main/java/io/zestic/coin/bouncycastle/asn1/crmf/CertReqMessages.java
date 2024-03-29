package io.zestic.coin.bouncycastle.asn1.crmf;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Sequence;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class CertReqMessages
        extends ASN1Encodable {

    private final ASN1Sequence content;

    private CertReqMessages(ASN1Sequence seq) {
        content = seq;
    }

    public static CertReqMessages getInstance(Object o) {
        if (o instanceof CertReqMessages) {
            return (CertReqMessages) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CertReqMessages((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CertReqMsg[] toCertReqMsgArray() {
        CertReqMsg[] result = new CertReqMsg[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = CertReqMsg.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * CertReqMessages ::= SEQUENCE SIZE (1..MAX) OF CertReqMsg
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
