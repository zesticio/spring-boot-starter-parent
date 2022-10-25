package in.zestic.common.coin.bouncycastle.crypto.generators;

import java.math.BigInteger;

import in.zestic.common.coin.bouncycastle.crypto.AsymmetricCipherKeyPair;
import in.zestic.common.coin.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import in.zestic.common.coin.bouncycastle.crypto.KeyGenerationParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.DHParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ElGamalKeyGenerationParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ElGamalParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import in.zestic.common.coin.bouncycastle.crypto.params.ElGamalPublicKeyParameters;

/**
 * a ElGamal key pair generator.
 * <p>
 * This generates keys consistent for use with ElGamal as described in page 164
 * of "Handbook of Applied Cryptography".
 */
public class ElGamalKeyPairGenerator
        implements AsymmetricCipherKeyPairGenerator {

    private ElGamalKeyGenerationParameters param;

    public void init(
            KeyGenerationParameters param) {
        this.param = (ElGamalKeyGenerationParameters) param;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        DHKeyGeneratorHelper helper = DHKeyGeneratorHelper.INSTANCE;
        ElGamalParameters egp = param.getParameters();
        DHParameters dhp = new DHParameters(egp.getP(), egp.getG(), null, egp.getL());

        BigInteger x = helper.calculatePrivate(dhp, param.getRandom());
        BigInteger y = helper.calculatePublic(dhp, x);

        return new AsymmetricCipherKeyPair(
                new ElGamalPublicKeyParameters(y, egp),
                new ElGamalPrivateKeyParameters(x, egp));
    }
}
