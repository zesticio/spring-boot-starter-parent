package io.zestic.coin.bouncycastle.asn1.icao;

import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface ICAOObjectIdentifiers {

    //
    // base id
    //
    String id_icao = "2.23.136";

    DERObjectIdentifier id_icao_mrtd = new DERObjectIdentifier(id_icao + ".1");
    DERObjectIdentifier id_icao_mrtd_security = new DERObjectIdentifier(id_icao_mrtd + ".1");
    DERObjectIdentifier id_icao_ldsSecurityObject = new DERObjectIdentifier(id_icao_mrtd_security + ".1");
}
