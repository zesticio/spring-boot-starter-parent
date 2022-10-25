package in.zestic.common.coin.bouncycastle.asn1.cms;

import in.zestic.common.coin.bouncycastle.asn1.DERObjectIdentifier;
import in.zestic.common.coin.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface CMSAttributes {

    DERObjectIdentifier contentType = PKCSObjectIdentifiers.pkcs_9_at_contentType;
    DERObjectIdentifier messageDigest = PKCSObjectIdentifiers.pkcs_9_at_messageDigest;
    DERObjectIdentifier signingTime = PKCSObjectIdentifiers.pkcs_9_at_signingTime;
    DERObjectIdentifier counterSignature = PKCSObjectIdentifiers.pkcs_9_at_counterSignature;
    DERObjectIdentifier contentHint = PKCSObjectIdentifiers.id_aa_contentHint;
}
