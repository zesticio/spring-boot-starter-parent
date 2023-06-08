package io.zestic.coin.bouncycastle.asn1.ocsp;

import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface OCSPObjectIdentifiers {

    String pkix_ocsp = "1.3.6.1.5.5.7.48.1";

    DERObjectIdentifier id_pkix_ocsp = new DERObjectIdentifier(pkix_ocsp);
    DERObjectIdentifier id_pkix_ocsp_basic = new DERObjectIdentifier(pkix_ocsp + ".1");

    //
    // extensions
    //
    DERObjectIdentifier id_pkix_ocsp_nonce = new DERObjectIdentifier(pkix_ocsp + ".2");
    DERObjectIdentifier id_pkix_ocsp_crl = new DERObjectIdentifier(pkix_ocsp + ".3");

    DERObjectIdentifier id_pkix_ocsp_response = new DERObjectIdentifier(pkix_ocsp + ".4");
    DERObjectIdentifier id_pkix_ocsp_nocheck = new DERObjectIdentifier(pkix_ocsp + ".5");
    DERObjectIdentifier id_pkix_ocsp_archive_cutoff = new DERObjectIdentifier(pkix_ocsp + ".6");
    DERObjectIdentifier id_pkix_ocsp_service_locator = new DERObjectIdentifier(pkix_ocsp + ".7");
}
