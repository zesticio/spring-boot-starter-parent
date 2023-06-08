package io.zestic.coin.bouncycastle.asn1.smime;

import io.zestic.coin.bouncycastle.asn1.DERSequence;
import io.zestic.coin.bouncycastle.asn1.DERSet;
import io.zestic.coin.bouncycastle.asn1.cms.Attribute;

public class SMIMECapabilitiesAttribute
        extends Attribute {

    public SMIMECapabilitiesAttribute(
            SMIMECapabilityVector capabilities) {
        super(SMIMEAttributes.smimeCapabilities,
                new DERSet(new DERSequence(capabilities.toDEREncodableVector())));
    }
}
