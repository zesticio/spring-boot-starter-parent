package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

import java.util.Enumeration;

public class ErrorMsgContent
        extends ASN1Encodable {

    private final PKIStatusInfo pKIStatusInfo;
    private DERInteger errorCode;
    private PKIFreeText errorDetails;

    private ErrorMsgContent(ASN1Sequence seq) {
        Enumeration en = seq.getObjects();

        pKIStatusInfo = PKIStatusInfo.getInstance(en.nextElement());

        while (en.hasMoreElements()) {
            Object o = en.nextElement();

            if (o instanceof DERInteger) {
                errorCode = DERInteger.getInstance(o);
            } else {
                errorDetails = PKIFreeText.getInstance(o);
            }
        }
    }

    public static ErrorMsgContent getInstance(Object o) {
        if (o instanceof ErrorMsgContent) {
            return (ErrorMsgContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new ErrorMsgContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public PKIStatusInfo getPKIStatusInfo() {
        return pKIStatusInfo;
    }

    public DERInteger getErrorCode() {
        return errorCode;
    }

    public PKIFreeText getErrorDetails() {
        return errorDetails;
    }

    /**
     * ErrorMsgContent ::= SEQUENCE {
     * pKIStatusInfo          PKIStatusInfo,
     * errorCode              INTEGER           OPTIONAL,
     * -- implementation-specific error codes
     * errorDetails           PKIFreeText       OPTIONAL
     * -- implementation-specific error details
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(pKIStatusInfo);
        addOptional(v, errorCode);
        addOptional(v, errorDetails);

        return new DERSequence(v);
    }

    private void addOptional(ASN1EncodableVector v, ASN1Encodable obj) {
        if (obj != null) {
            v.add(obj);
        }
    }
}
