package in.zestic.common.coin.bouncycastle.asn1.x509.qualified;

import in.zestic.common.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface ETSIQCObjectIdentifiers {

    //
    // base id
    //
    String id_etsi_qcs = "0.4.0.1862.1";

    DERObjectIdentifier id_etsi_qcs_QcCompliance = new DERObjectIdentifier(id_etsi_qcs + ".1");
    DERObjectIdentifier id_etsi_qcs_LimiteValue = new DERObjectIdentifier(id_etsi_qcs + ".2");
    DERObjectIdentifier id_etsi_qcs_RetentionPeriod = new DERObjectIdentifier(id_etsi_qcs + ".3");
    DERObjectIdentifier id_etsi_qcs_QcSSCD = new DERObjectIdentifier(id_etsi_qcs + ".4");
}
