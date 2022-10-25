package in.zestic.common.coin.bouncycastle.asn1.tsp;

import in.zestic.common.coin.bouncycastle.asn1.*;
import in.zestic.common.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class MessageImprint
        extends ASN1Encodable {

    AlgorithmIdentifier hashAlgorithm;
    byte[] hashedMessage;

    public MessageImprint(
            ASN1Sequence seq) {
        this.hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
        this.hashedMessage = ASN1OctetString.getInstance(seq.getObjectAt(1)).getOctets();
    }

    public MessageImprint(
            AlgorithmIdentifier hashAlgorithm,
            byte[] hashedMessage) {
        this.hashAlgorithm = hashAlgorithm;
        this.hashedMessage = hashedMessage;
    }

    /**
     * @param o
     * @return a MessageImprint object.
     */
    public static MessageImprint getInstance(Object o) {
        if (o == null || o instanceof MessageImprint) {
            return (MessageImprint) o;
        } else if (o instanceof ASN1Sequence) {
            return new MessageImprint((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Bad object in factory.");
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return hashAlgorithm;
    }

    public byte[] getHashedMessage() {
        return hashedMessage;
    }

    /**
     *    MessageImprint ::= SEQUENCE  {
     *       hashAlgorithm                AlgorithmIdentifier,
     *       hashedMessage                OCTET STRING  }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(hashAlgorithm);
        v.add(new DEROctetString(hashedMessage));

        return new DERSequence(v);
    }
}
