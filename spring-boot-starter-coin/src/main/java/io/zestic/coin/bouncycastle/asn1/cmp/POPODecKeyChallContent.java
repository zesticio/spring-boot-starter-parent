package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.ASN1Encodable;
import io.zestic.coin.bouncycastle.asn1.ASN1Sequence;
import io.zestic.coin.bouncycastle.asn1.DERObject;

public class POPODecKeyChallContent
        extends ASN1Encodable {

    private final ASN1Sequence content;

    private POPODecKeyChallContent(ASN1Sequence seq) {
        content = seq;
    }

    public static POPODecKeyChallContent getInstance(Object o) {
        if (o instanceof POPODecKeyChallContent) {
            return (POPODecKeyChallContent) o;
        }

        if (o instanceof ASN1Sequence) {
            return new POPODecKeyChallContent((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public Challenge[] toChallengeArray() {
        Challenge[] result = new Challenge[content.size()];

        for (int i = 0; i != result.length; i++) {
            result[i] = Challenge.getInstance(content.getObjectAt(i));
        }

        return result;
    }

    /**
     * POPODecKeyChallContent ::= SEQUENCE OF Challenge
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        return content;
    }
}
