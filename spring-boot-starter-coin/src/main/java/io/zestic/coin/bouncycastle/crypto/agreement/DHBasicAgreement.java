package io.zestic.coin.bouncycastle.crypto.agreement;

import java.math.BigInteger;

import io.zestic.coin.bouncycastle.crypto.BasicAgreement;
import io.zestic.coin.bouncycastle.crypto.CipherParameters;
import io.zestic.coin.bouncycastle.crypto.params.DHPrivateKeyParameters;
import io.zestic.coin.bouncycastle.crypto.params.ParametersWithRandom;
import io.zestic.coin.bouncycastle.crypto.params.AsymmetricKeyParameter;
import io.zestic.coin.bouncycastle.crypto.params.DHParameters;
import io.zestic.coin.bouncycastle.crypto.params.DHPublicKeyParameters;

/**
 * a Diffie-Hellman key agreement class.
 * <p>
 * note: This is only the basic algorithm, it doesn't take advantage of long
 * term public keys if they are available. See the DHAgreement class for a
 * "better" implementation.
 */
public class DHBasicAgreement
        implements BasicAgreement {

    private DHPrivateKeyParameters key;
    private DHParameters dhParams;

    public void init(
            CipherParameters param) {
        AsymmetricKeyParameter kParam;

        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom rParam = (ParametersWithRandom) param;
            kParam = (AsymmetricKeyParameter) rParam.getParameters();
        } else {
            kParam = (AsymmetricKeyParameter) param;
        }

        if (!(kParam instanceof DHPrivateKeyParameters)) {
            throw new IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
        }

        this.key = (DHPrivateKeyParameters) kParam;
        this.dhParams = key.getParameters();
    }

    /**
     * given a short term public key from a given party calculate the next
     * message in the agreement sequence.
     */
    public BigInteger calculateAgreement(
            CipherParameters pubKey) {
        DHPublicKeyParameters pub = (DHPublicKeyParameters) pubKey;

        if (!pub.getParameters().equals(dhParams)) {
            throw new IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
        }

        return pub.getY().modPow(key.getX(), dhParams.getP());
    }
}
