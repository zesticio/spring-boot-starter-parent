package io.zestic.coin.bouncycastle.asn1.x509;

import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;

/**
 * CertPolicyId, used in the CertificatePolicies and PolicyMappings X509V3
 * Extensions.
 *
 *     CertPolicyId ::= OBJECT IDENTIFIER */
public class CertPolicyId extends DERObjectIdentifier {

    public CertPolicyId(String id) {
        super(id);
    }
}
