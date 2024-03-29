package io.zestic.coin.bouncycastle.asn1.ess;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;
import io.zestic.coin.bouncycastle.asn1.x509.IssuerSerial;

public class ESSCertIDv2
        extends ASN1Encodable {

    private static final AlgorithmIdentifier DEFAULT_ALG_ID = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
    private final AlgorithmIdentifier hashAlgorithm;
    private final byte[] certHash;
    private IssuerSerial issuerSerial;

    public ESSCertIDv2(
            ASN1Sequence seq) {
        if (seq.size() != 2 && seq.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + seq.size());
        }

        int count = 0;

        if (seq.getObjectAt(0) instanceof ASN1OctetString) {
            // Default value
            this.hashAlgorithm = DEFAULT_ALG_ID;
        } else {
            this.hashAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(count++).getDERObject());
        }

        this.certHash = ASN1OctetString.getInstance(seq.getObjectAt(count++).getDERObject()).getOctets();

        if (seq.size() > count) {
            this.issuerSerial = new IssuerSerial(ASN1Sequence.getInstance(seq.getObjectAt(count).getDERObject()));
        }
    }

    public ESSCertIDv2(
            AlgorithmIdentifier algId,
            byte[] certHash) {
        this(algId, certHash, null);
    }

    public ESSCertIDv2(
            AlgorithmIdentifier algId,
            byte[] certHash,
            IssuerSerial issuerSerial) {
        if (algId == null) {
            // Default value
            this.hashAlgorithm = DEFAULT_ALG_ID;
        } else {
            this.hashAlgorithm = algId;
        }

        this.certHash = certHash;
        this.issuerSerial = issuerSerial;
    }

    public static ESSCertIDv2 getInstance(
            Object o) {
        if (o == null || o instanceof ESSCertIDv2) {
            return (ESSCertIDv2) o;
        } else if (o instanceof ASN1Sequence) {
            return new ESSCertIDv2((ASN1Sequence) o);
        }

        throw new IllegalArgumentException(
                "unknown object in 'ESSCertIDv2' factory : "
                        + o.getClass().getName() + ".");
    }

    public AlgorithmIdentifier getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public byte[] getCertHash() {
        return certHash;
    }

    public IssuerSerial getIssuerSerial() {
        return issuerSerial;
    }

    /**
     * ESSCertIDv2 ::=  SEQUENCE {
     * hashAlgorithm     AlgorithmIdentifier
     * DEFAULT {algorithm id-sha256},
     * certHash          Hash,
     * issuerSerial      IssuerSerial OPTIONAL
     * }
     * <p>
     * Hash ::= OCTET STRING
     * <p>
     * IssuerSerial ::= SEQUENCE {
     * issuer         GeneralNames,
     * serialNumber   CertificateSerialNumber
     * }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        if (!hashAlgorithm.equals(DEFAULT_ALG_ID)) {
            v.add(hashAlgorithm);
        }

        v.add(new DEROctetString(certHash).toASN1Object());

        if (issuerSerial != null) {
            v.add(issuerSerial);
        }

        return new DERSequence(v);
    }

}
