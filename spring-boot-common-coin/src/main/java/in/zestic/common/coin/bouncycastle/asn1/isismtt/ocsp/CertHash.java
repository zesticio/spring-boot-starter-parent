package in.zestic.common.coin.bouncycastle.asn1.isismtt.ocsp;

import in.zestic.common.coin.bouncycastle.asn1.*;
import in.zestic.common.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CertHash extends ASN1Encodable {

    private AlgorithmIdentifier hashAlgorithm;
    private byte[] certificateHash;

    public static CertHash getInstance(Object obj) {
        if (obj == null || obj instanceof CertHash) {
            return (CertHash) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new CertHash((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("illegal object in getInstance: "
                + obj.getClass().getName());
    }

    /**
     * Constructor from ASN1Sequence.
     * The sequence is of type CertHash:
     * CertHash ::= SEQUENCE { hashAlgorithm AlgorithmIdentifier,
     * certificateHash OCTET STRING }
     *
     * @param seq The ASN.1 sequence.
     */
    private CertHash(ASN1Sequence seq) {
        if (seq.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: "
                    + seq.size());
        }
        hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
        certificateHash = DEROctetString.getInstance(seq.getObjectAt(1)).getOctets();
    }

    /**
     * Constructor from a given details.
     *
     * @param hashAlgorithm   The hash algorithm identifier.
     * @param certificateHash The hash of the whole DER encoding of the
     *                        certificate.
     */
    public CertHash(AlgorithmIdentifier hashAlgorithm, byte[] certificateHash) {
        this.hashAlgorithm = hashAlgorithm;
        this.certificateHash = new byte[certificateHash.length];
        System.arraycopy(certificateHash, 0, this.certificateHash, 0,
                certificateHash.length);
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return hashAlgorithm;
    }

    public byte[] getCertificateHash() {
        return certificateHash;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * Returns:
     * CertHash ::= SEQUENCE { hashAlgorithm AlgorithmIdentifier,
     * certificateHash OCTET STRING }
     *
     * @return a DERObject
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        vec.add(hashAlgorithm);
        vec.add(new DEROctetString(certificateHash));
        return new DERSequence(vec);
    }
}
