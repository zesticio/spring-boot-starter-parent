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

package io.zestic.coin.utils;

import io.zestic.coin.core.Base58;
import io.zestic.coin.core.ECKey;
import io.zestic.coin.core.NetworkParameters;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author deebendukumar
 */
public class Passport {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Passport.class);

    private Passport() {
    }

    public static String generate(String value) {
        Objects.requireNonNull(value, "value must not be null");
        NetworkParameters params = NetworkParameters.prodNet();
        ECKey key = new ECKey(Base58.decodeToBigInteger(value));
        return key.toAddress(params).toString();
    }

    public static String generate(Long value) {
        Objects.requireNonNull(value, "value must not be null");
        NetworkParameters params = NetworkParameters.prodNet();
        ECKey key = new ECKey(Base58.decodeToBigInteger(Long.toString(value)));
        return key.toAddress(params).toString();
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
