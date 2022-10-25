package in.zestic.common.coin.bouncycastle.asn1.smime;

import in.zestic.common.coin.bouncycastle.asn1.DERSequence;
import in.zestic.common.coin.bouncycastle.asn1.DERSet;
import in.zestic.common.coin.bouncycastle.asn1.cms.Attribute;

public class SMIMECapabilitiesAttribute
        extends Attribute {

    public SMIMECapabilitiesAttribute(
            SMIMECapabilityVector capabilities) {
        super(SMIMEAttributes.smimeCapabilities,
                new DERSet(new DERSequence(capabilities.toDEREncodableVector())));
    }
}
