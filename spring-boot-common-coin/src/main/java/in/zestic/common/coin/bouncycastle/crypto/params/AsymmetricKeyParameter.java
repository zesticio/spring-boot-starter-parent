package in.zestic.common.coin.bouncycastle.crypto.params;

import in.zestic.common.coin.bouncycastle.crypto.CipherParameters;

public class AsymmetricKeyParameter
        implements CipherParameters {

    boolean privateKey;

    public AsymmetricKeyParameter(
            boolean privateKey) {
        this.privateKey = privateKey;
    }

    public boolean isPrivate() {
        return privateKey;
    }
}
