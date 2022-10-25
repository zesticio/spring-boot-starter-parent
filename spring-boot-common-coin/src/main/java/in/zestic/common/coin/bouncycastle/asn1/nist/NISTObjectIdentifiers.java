package in.zestic.common.coin.bouncycastle.asn1.nist;

import in.zestic.common.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface NISTObjectIdentifiers {
    //
    // NIST
    //     iso/itu(2) joint-assign(16) us(840) organization(1) gov(101) csor(3) 

    //
    // nistalgorithms(4)
    //
    String nistAlgorithm = "2.16.840.1.101.3.4";

    DERObjectIdentifier id_sha256 = new DERObjectIdentifier(nistAlgorithm + ".2.1");
    DERObjectIdentifier id_sha384 = new DERObjectIdentifier(nistAlgorithm + ".2.2");
    DERObjectIdentifier id_sha512 = new DERObjectIdentifier(nistAlgorithm + ".2.3");
    DERObjectIdentifier id_sha224 = new DERObjectIdentifier(nistAlgorithm + ".2.4");

    String aes = nistAlgorithm + ".1";

    DERObjectIdentifier id_aes128_ECB = new DERObjectIdentifier(aes + ".1");
    DERObjectIdentifier id_aes128_CBC = new DERObjectIdentifier(aes + ".2");
    DERObjectIdentifier id_aes128_OFB = new DERObjectIdentifier(aes + ".3");
    DERObjectIdentifier id_aes128_CFB = new DERObjectIdentifier(aes + ".4");
    DERObjectIdentifier id_aes128_wrap = new DERObjectIdentifier(aes + ".5");
    DERObjectIdentifier id_aes128_GCM = new DERObjectIdentifier(aes + ".6");
    DERObjectIdentifier id_aes128_CCM = new DERObjectIdentifier(aes + ".7");

    DERObjectIdentifier id_aes192_ECB = new DERObjectIdentifier(aes + ".21");
    DERObjectIdentifier id_aes192_CBC = new DERObjectIdentifier(aes + ".22");
    DERObjectIdentifier id_aes192_OFB = new DERObjectIdentifier(aes + ".23");
    DERObjectIdentifier id_aes192_CFB = new DERObjectIdentifier(aes + ".24");
    DERObjectIdentifier id_aes192_wrap = new DERObjectIdentifier(aes + ".25");
    DERObjectIdentifier id_aes192_GCM = new DERObjectIdentifier(aes + ".26");
    DERObjectIdentifier id_aes192_CCM = new DERObjectIdentifier(aes + ".27");

    DERObjectIdentifier id_aes256_ECB = new DERObjectIdentifier(aes + ".41");
    DERObjectIdentifier id_aes256_CBC = new DERObjectIdentifier(aes + ".42");
    DERObjectIdentifier id_aes256_OFB = new DERObjectIdentifier(aes + ".43");
    DERObjectIdentifier id_aes256_CFB = new DERObjectIdentifier(aes + ".44");
    DERObjectIdentifier id_aes256_wrap = new DERObjectIdentifier(aes + ".45");
    DERObjectIdentifier id_aes256_GCM = new DERObjectIdentifier(aes + ".46");
    DERObjectIdentifier id_aes256_CCM = new DERObjectIdentifier(aes + ".47");

    //
    // signatures
    //
    DERObjectIdentifier id_dsa_with_sha2 = new DERObjectIdentifier(nistAlgorithm + ".3");

    DERObjectIdentifier dsa_with_sha224 = new DERObjectIdentifier(id_dsa_with_sha2 + ".1");
    DERObjectIdentifier dsa_with_sha256 = new DERObjectIdentifier(id_dsa_with_sha2 + ".2");
    DERObjectIdentifier dsa_with_sha384 = new DERObjectIdentifier(id_dsa_with_sha2 + ".3");
    DERObjectIdentifier dsa_with_sha512 = new DERObjectIdentifier(id_dsa_with_sha2 + ".4");
}
