package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.util.Enumeration;

public class SignerInfo
        extends ASN1Encodable {

    private final DERInteger version;
    private final SignerIdentifier sid;
    private final AlgorithmIdentifier digAlgorithm;
    private final ASN1Set authenticatedAttributes;
    private final AlgorithmIdentifier digEncryptionAlgorithm;
    private final ASN1OctetString encryptedDigest;
    private final ASN1Set unauthenticatedAttributes;

    public SignerInfo(
            SignerIdentifier sid,
            AlgorithmIdentifier digAlgorithm,
            ASN1Set authenticatedAttributes,
            AlgorithmIdentifier digEncryptionAlgorithm,
            ASN1OctetString encryptedDigest,
            ASN1Set unauthenticatedAttributes) {
        if (sid.isTagged()) {
            this.version = new DERInteger(3);
        } else {
            this.version = new DERInteger(1);
        }

        this.sid = sid;
        this.digAlgorithm = digAlgorithm;
        this.authenticatedAttributes = authenticatedAttributes;
        this.digEncryptionAlgorithm = digEncryptionAlgorithm;
        this.encryptedDigest = encryptedDigest;
        this.unauthenticatedAttributes = unauthenticatedAttributes;
    }

    public SignerInfo(
            ASN1Sequence seq) {
        Enumeration e = seq.getObjects();

        version = (DERInteger) e.nextElement();
        sid = SignerIdentifier.getInstance(e.nextElement());
        digAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());

        Object obj = e.nextElement();

        if (obj instanceof ASN1TaggedObject) {
            authenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject) obj, false);

            digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
        } else {
            authenticatedAttributes = null;
            digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(obj);
        }

        encryptedDigest = DEROctetString.getInstance(e.nextElement());

        if (e.hasMoreElements()) {
            unauthenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject) e.nextElement(), false);
        } else {
            unauthenticatedAttributes = null;
        }
    }

    public static SignerInfo getInstance(
            Object o)
            throws IllegalArgumentException {
        if (o == null || o instanceof SignerInfo) {
            return (SignerInfo) o;
        } else if (o instanceof ASN1Sequence) {
            return new SignerInfo((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("unknown object in factory: " + o.getClass().getName());
    }

    public DERInteger getVersion() {
        return version;
    }

    public SignerIdentifier getSID() {
        return sid;
    }

    public ASN1Set getAuthenticatedAttributes() {
        return authenticatedAttributes;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return digAlgorithm;
    }

    public ASN1OctetString getEncryptedDigest() {
        return encryptedDigest;
    }

    public AlgorithmIdentifier getDigestEncryptionAlgorithm() {
        return digEncryptionAlgorithm;
    }

    public ASN1Set getUnauthenticatedAttributes() {
        return unauthenticatedAttributes;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * SignerInfo ::= SEQUENCE {
     * version Version,
     * SignerIdentifier sid,
     * digestAlgorithm DigestAlgorithmIdentifier,
     * authenticatedAttributes [0] IMPLICIT Attributes OPTIONAL,
     * digestEncryptionAlgorithm DigestEncryptionAlgorithmIdentifier,
     * encryptedDigest EncryptedDigest,
     * unauthenticatedAttributes [1] IMPLICIT Attributes OPTIONAL
     * }
     * <p>
     * EncryptedDigest ::= OCTET STRING
     * <p>
     * DigestAlgorithmIdentifier ::= AlgorithmIdentifier
     * <p>
     * DigestEncryptionAlgorithmIdentifier ::= AlgorithmIdentifier
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(version);
        v.add(sid);
        v.add(digAlgorithm);

        if (authenticatedAttributes != null) {
            v.add(new DERTaggedObject(false, 0, authenticatedAttributes));
        }

        v.add(digEncryptionAlgorithm);
        v.add(encryptedDigest);

        if (unauthenticatedAttributes != null) {
            v.add(new DERTaggedObject(false, 1, unauthenticatedAttributes));
        }

        return new DERSequence(v);
    }
}
