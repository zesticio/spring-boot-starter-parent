package io.zestic.coin.bouncycastle.crypto.agreement.kdf;

import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;
import io.zestic.coin.bouncycastle.crypto.DataLengthException;
import io.zestic.coin.bouncycastle.crypto.DerivationFunction;
import io.zestic.coin.bouncycastle.crypto.DerivationParameters;
import io.zestic.coin.bouncycastle.crypto.Digest;
import io.zestic.coin.bouncycastle.crypto.generators.KDF2BytesGenerator;
import io.zestic.coin.bouncycastle.crypto.params.KDFParameters;
import io.zestic.coin.bouncycastle.asn1.ASN1EncodableVector;
import io.zestic.coin.bouncycastle.asn1.DERNull;
import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;
import io.zestic.coin.bouncycastle.asn1.DEROctetString;
import io.zestic.coin.bouncycastle.asn1.DERSequence;
import io.zestic.coin.bouncycastle.asn1.DERTaggedObject;

/**
 * X9.63 based key derivation function for ECDH CMS.
 */
public class ECDHKEKGenerator
        implements DerivationFunction {

    private DerivationFunction kdf;

    private DERObjectIdentifier algorithm;
    private int keySize;
    private byte[] z;

    public ECDHKEKGenerator(
            Digest digest) {
        this.kdf = new KDF2BytesGenerator(digest);
    }

    public void init(DerivationParameters param) {
        DHKDFParameters params = (DHKDFParameters) param;

        this.algorithm = params.getAlgorithm();
        this.keySize = params.getKeySize();
        this.z = params.getZ();
    }

    public Digest getDigest() {
        return kdf.getDigest();
    }

    public int generateBytes(byte[] out, int outOff, int len)
            throws DataLengthException, IllegalArgumentException {
        // ECC-CMS-SharedInfo
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(new AlgorithmIdentifier(algorithm, new DERNull()));
        v.add(new DERTaggedObject(true, 2, new DEROctetString(integerToBytes(keySize))));

        kdf.init(new KDFParameters(z, new DERSequence(v).getDEREncoded()));

        return kdf.generateBytes(out, outOff, len);
    }

    private byte[] integerToBytes(int keySize) {
        byte[] val = new byte[4];

        val[0] = (byte) (keySize >> 24);
        val[1] = (byte) (keySize >> 16);
        val[2] = (byte) (keySize >> 8);
        val[3] = (byte) keySize;

        return val;
    }
}
