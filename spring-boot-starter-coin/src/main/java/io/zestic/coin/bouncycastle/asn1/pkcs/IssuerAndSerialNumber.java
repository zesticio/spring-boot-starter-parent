package io.zestic.coin.bouncycastle.asn1.pkcs;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.X509Name;

import java.math.BigInteger;

public class IssuerAndSerialNumber
        extends ASN1Encodable {

    X509Name name;
    DERInteger certSerialNumber;

    public static IssuerAndSerialNumber getInstance(
            Object obj) {
        if (obj instanceof IssuerAndSerialNumber) {
            return (IssuerAndSerialNumber) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new IssuerAndSerialNumber((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public IssuerAndSerialNumber(
            ASN1Sequence seq) {
        this.name = X509Name.getInstance(seq.getObjectAt(0));
        this.certSerialNumber = (DERInteger) seq.getObjectAt(1);
    }

    public IssuerAndSerialNumber(
            X509Name name,
            BigInteger certSerialNumber) {
        this.name = name;
        this.certSerialNumber = new DERInteger(certSerialNumber);
    }

    public IssuerAndSerialNumber(
            X509Name name,
            DERInteger certSerialNumber) {
        this.name = name;
        this.certSerialNumber = certSerialNumber;
    }

    public X509Name getName() {
        return name;
    }

    public DERInteger getCertificateSerialNumber() {
        return certSerialNumber;
    }

    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(name);
        v.add(certSerialNumber);

        return new DERSequence(v);
    }
}
