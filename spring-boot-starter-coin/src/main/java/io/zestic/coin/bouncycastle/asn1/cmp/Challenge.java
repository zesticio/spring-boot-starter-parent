package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge extends ASN1Encodable {

    private final ASN1OctetString witness;
    private final ASN1OctetString challenge;
    private AlgorithmIdentifier owf;

    private Challenge(ASN1Sequence seq) {
        int index = 0;

        if (seq.size() == 3) {
            owf = AlgorithmIdentifier.getInstance(seq.getObjectAt(index++));
        }

        witness = ASN1OctetString.getInstance(seq.getObjectAt(index++));
        challenge = ASN1OctetString.getInstance(seq.getObjectAt(index));
    }

    public static Challenge getInstance(Object o) {
        if (o instanceof Challenge) {
            return (Challenge) o;
        }

        if (o instanceof ASN1Sequence) {
            return new Challenge((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public AlgorithmIdentifier getOwf() {
        return owf;
    }

    /**
     * Challenge ::= SEQUENCE {
     * owf                 AlgorithmIdentifier  OPTIONAL,
     * <p>
     * -- MUST be present in the first Challenge; MAY be omitted in
     * -- any subsequent Challenge in POPODecKeyChallContent (if
     * -- omitted, then the owf used in the immediately preceding
     * -- Challenge is to be used).
     * <p>
     * witness             OCTET STRING,
     * -- the result of applying the one-way function (owf) to a
     * -- randomly-generated INTEGER, A.  [Note that a different
     * -- INTEGER MUST be used for each Challenge.]
     * challenge           OCTET STRING
     * -- the encryption (under the public key for which the cert.
     * -- request is being made) of Rand, where Rand is specified as
     * --   Rand ::= SEQUENCE {
     * --      int      INTEGER,
     * --       - the randomly-generated INTEGER A (above)
     * --      sender   GeneralName
     * --       - the sender's name (as included in PKIHeader)
     * --   }
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        addOptional(v, owf);
        v.add(witness);
        v.add(challenge);

        return new DERSequence(v);
    }

    private void addOptional(ASN1EncodableVector v, ASN1Encodable obj) {
        if (obj != null) {
            v.add(obj);
        }
    }
}
