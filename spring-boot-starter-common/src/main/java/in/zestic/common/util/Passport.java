/*
 *
 * Version:  1.0.0
 *
 * Authors:  Kumar <kumar@elitasolutions.in>
 *
 **************************************************************************
 *
 * Copyright (c) 2009,2010,2011 Elita IT Solutions
 * All Rights Reserved.
 *
 **************************************************************************
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Elita IT Solutions and its suppliers, if any.
 * The intellectual and technical concepts contained
 * herein are proprietary to Elita IT Solutions
 * and its suppliers and may be covered by India and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Elita IT Solutions.
 *
 * The above copyright notice and this permission notice must be included
 * in all copies of this file.
 *
 * Description:
 */
package in.zestic.common.util;

import in.zestic.common.coin.core.Base58;
import in.zestic.common.coin.core.ECKey;
import in.zestic.common.coin.core.NetworkParameters;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author deebendukumar
 */
public class Passport {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Passport.class);

    private Passport() {
    }

    public static String generate(String msisdn) {
        NetworkParameters params = NetworkParameters.prodNet();
        BigInteger privKey = Base58.decodeToBigInteger(msisdn);
        ECKey key = new ECKey(privKey);
        logger.info("Address from private key is: " + key.toAddress(params).toString());
        String passport = key.toAddress(params).toString();
        return passport;
    }

    public static void main(String[] args) {
        System.out.println(Passport.generate("919872177944"));
    }

    private String generateUniqueId() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String digest;
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        digest = Hex.encodeHexString(salt.digest());
        return digest;
    }
}
