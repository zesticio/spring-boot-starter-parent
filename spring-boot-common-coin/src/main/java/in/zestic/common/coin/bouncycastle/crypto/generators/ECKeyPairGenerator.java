package in.zestic.common.coin.bouncycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;

import in.zestic.common.coin.bouncycastle.crypto.AsymmetricCipherKeyPair;
import in.zestic.common.coin.bouncycastle.crypto.KeyGenerationParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ECDomainParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ECKeyGenerationParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ECPrivateKeyParameters;
import in.zestic.common.coin.bouncycastle.math.ec.ECConstants;
import in.zestic.common.coin.bouncycastle.math.ec.ECPoint;
import in.zestic.common.coin.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import in.zestic.common.coin.bouncycastle.crypto.params.ECPublicKeyParameters;

public class ECKeyPairGenerator
        implements AsymmetricCipherKeyPairGenerator, ECConstants {

    ECDomainParameters params;
    SecureRandom random;

    public void init(
            KeyGenerationParameters param) {
        ECKeyGenerationParameters ecP = (ECKeyGenerationParameters) param;

        this.random = ecP.getRandom();
        this.params = ecP.getDomainParameters();
    }

    /**
     * Given the domain parameters this routine generates an EC key pair in
     * accordance with X9.62 section 5.2.1 pages 26, 27.
     */
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger n = params.getN();
        int nBitLength = n.bitLength();
        BigInteger d;

        do {
            d = new BigInteger(nBitLength, random);
        } while (d.equals(ZERO) || (d.compareTo(n) >= 0));

        ECPoint Q = params.getG().multiply(d);

        return new AsymmetricCipherKeyPair(
                new ECPublicKeyParameters(Q, params),
                new ECPrivateKeyParameters(d, params));
    }
}
