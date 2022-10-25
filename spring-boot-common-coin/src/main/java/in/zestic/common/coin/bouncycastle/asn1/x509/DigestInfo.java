package in.zestic.common.coin.bouncycastle.asn1.x509;

import java.util.Enumeration;

import in.zestic.common.coin.bouncycastle.asn1.ASN1Encodable;
import in.zestic.common.coin.bouncycastle.asn1.ASN1EncodableVector;
import in.zestic.common.coin.bouncycastle.asn1.ASN1OctetString;
import in.zestic.common.coin.bouncycastle.asn1.ASN1Sequence;
import in.zestic.common.coin.bouncycastle.asn1.ASN1TaggedObject;
import in.zestic.common.coin.bouncycastle.asn1.DERObject;
import in.zestic.common.coin.bouncycastle.asn1.DEROctetString;
import in.zestic.common.coin.bouncycastle.asn1.DERSequence;

/**
 * The DigestInfo object.
 * DigestInfo::=SEQUENCE{
 *          digestAlgorithm  AlgorithmIdentifier,
 *          digest OCTET STRING } */
public class DigestInfo
        extends ASN1Encodable {

    private byte[] digest;
    private AlgorithmIdentifier algId;

    public static DigestInfo getInstance(
            ASN1TaggedObject obj,
            boolean explicit) {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static DigestInfo getInstance(
            Object obj) {
        if (obj instanceof DigestInfo) {
            return (DigestInfo) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new DigestInfo((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public DigestInfo(
            AlgorithmIdentifier algId,
            byte[] digest) {
        this.digest = digest;
        this.algId = algId;
    }

    public DigestInfo(
            ASN1Sequence obj) {
        Enumeration e = obj.getObjects();

        algId = AlgorithmIdentifier.getInstance(e.nextElement());
        digest = ASN1OctetString.getInstance(e.nextElement()).getOctets();
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return algId;
    }

    public byte[] getDigest() {
        return digest;
    }

    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(algId);
        v.add(new DEROctetString(digest));

        return new DERSequence(v);
    }
}
