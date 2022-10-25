package in.zestic.common.coin.bouncycastle.crypto.signers;

import in.zestic.common.coin.bouncycastle.crypto.CipherParameters;
import in.zestic.common.coin.bouncycastle.crypto.CryptoException;
import in.zestic.common.coin.bouncycastle.crypto.DataLengthException;
import in.zestic.common.coin.bouncycastle.crypto.params.ParametersWithRandom;
import in.zestic.common.coin.bouncycastle.crypto.AsymmetricBlockCipher;
import in.zestic.common.coin.bouncycastle.crypto.Digest;
import in.zestic.common.coin.bouncycastle.crypto.Signer;
import in.zestic.common.coin.bouncycastle.crypto.params.AsymmetricKeyParameter;
import in.zestic.common.coin.bouncycastle.util.Arrays;

public class GenericSigner
        implements Signer {

    private final AsymmetricBlockCipher engine;
    private final Digest digest;
    private boolean forSigning;

    public GenericSigner(
            AsymmetricBlockCipher engine,
            Digest digest) {
        this.engine = engine;
        this.digest = digest;
    }

    /**
     * initialise the signer for signing or verification.
     *
     * @param forSigning true if for signing, false otherwise
     * @param parameters necessary parameters.
     */
    public void init(
            boolean forSigning,
            CipherParameters parameters) {
        this.forSigning = forSigning;
        AsymmetricKeyParameter k;

        if (parameters instanceof ParametersWithRandom) {
            k = (AsymmetricKeyParameter) ((ParametersWithRandom) parameters).getParameters();
        } else {
            k = (AsymmetricKeyParameter) parameters;
        }

        if (forSigning && !k.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        }

        if (!forSigning && k.isPrivate()) {
            throw new IllegalArgumentException("verification requires public key");
        }

        reset();

        engine.init(forSigning, parameters);
    }

    /**
     * update the internal digest with the byte b
     */
    public void update(
            byte input) {
        digest.update(input);
    }

    /**
     * update the internal digest with the byte array in
     */
    public void update(
            byte[] input,
            int inOff,
            int length) {
        digest.update(input, inOff, length);
    }

    /**
     * Generate a signature for the message we've been loaded with using the key
     * we were initialised with.
     */
    public byte[] generateSignature()
            throws CryptoException, DataLengthException {
        if (!forSigning) {
            throw new IllegalStateException("GenericSigner not initialised for signature generation.");
        }

        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);

        return engine.processBlock(hash, 0, hash.length);
    }

    /**
     * return true if the internal state represents the signature described in
     * the passed in array.
     */
    public boolean verifySignature(
            byte[] signature) {
        if (forSigning) {
            throw new IllegalStateException("GenericSigner not initialised for verification");
        }

        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);

        try {
            byte[] sig = engine.processBlock(signature, 0, signature.length);

            return Arrays.constantTimeAreEqual(sig, hash);
        } catch (Exception e) {
            return false;
        }
    }

    public void reset() {
        digest.reset();
    }
}
