/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.zestic.common.jwt;

import java.util.Base64;

public class JWTDecoder {

    static JWTDecodeResult decodeToken(String token, String key) {
        try {
            final var parts = partitionToken(token);
            final var headerJson = decodeBase64(parts[0]);
            final var header = JWTHeader.fromJson(headerJson);
            final var payload = decodeBase64(parts[1]);

            if (verifySignature(header.getAlg(), key, parts[0] + parts[1], parts[2])) {
                return new JWTDecodeResult(payload, header);
            }
            return new JWTDecodeResult(new JWTException(JWTExceptionType.INVALID_SIGNATURE));
        } catch (JWTException e) {
            return new JWTDecodeResult(e);
        }
    }

    protected static String[] partitionToken(String token) throws JWTException {
        final var parts = token.split("\\.");
        if (parts.length != 3) throw new JWTException(JWTExceptionType.NOT_ENOUGH_SEGMENTS);
        return parts;
    }

    protected static boolean verifySignature(JWTAlgorithm algorithm, String key, String signingInput, String signature) throws JWTException {
        return JWTEncoder.signHmac(signingInput, key, algorithm).equals(signature);
    }

    protected static String decodeBase64(String s) {
        return new String(Base64.getDecoder().decode(s));
    }
}
