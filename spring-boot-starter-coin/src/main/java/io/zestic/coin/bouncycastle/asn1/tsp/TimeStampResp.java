package io.zestic.coin.bouncycastle.asn1.tsp;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.cmp.PKIStatusInfo;
import io.zestic.coin.bouncycastle.asn1.cms.ContentInfo;

import java.util.Enumeration;

public class TimeStampResp
        extends ASN1Encodable {

    PKIStatusInfo pkiStatusInfo;

    ContentInfo timeStampToken;

    public TimeStampResp(ASN1Sequence seq) {

        Enumeration e = seq.getObjects();

        // status
        pkiStatusInfo = PKIStatusInfo.getInstance(e.nextElement());

        if (e.hasMoreElements()) {
            timeStampToken = ContentInfo.getInstance(e.nextElement());
        }
    }

    public TimeStampResp(PKIStatusInfo pkiStatusInfo, ContentInfo timeStampToken) {
        this.pkiStatusInfo = pkiStatusInfo;
        this.timeStampToken = timeStampToken;
    }

    public static TimeStampResp getInstance(Object o) {
        if (o == null || o instanceof TimeStampResp) {
            return (TimeStampResp) o;
        } else if (o instanceof ASN1Sequence) {
            return new TimeStampResp((ASN1Sequence) o);
        }

        throw new IllegalArgumentException(
                "unknown object in 'TimeStampResp' factory : "
                        + o.getClass().getName() + ".");
    }

    public PKIStatusInfo getStatus() {
        return pkiStatusInfo;
    }

    public ContentInfo getTimeStampToken() {
        return timeStampToken;
    }

    /**
     * TimeStampResp ::= SEQUENCE  {
     * status                  PKIStatusInfo,
     * timeStampToken          TimeStampToken     OPTIONAL  }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(pkiStatusInfo);
        if (timeStampToken != null) {
            v.add(timeStampToken);
        }

        return new DERSequence(v);
    }
}
