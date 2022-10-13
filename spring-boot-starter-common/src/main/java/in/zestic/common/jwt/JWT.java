package in.zestic.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.Optional;

@Setter
@Getter
public class JWT {

    private final String token;
    private JWTHeader header;
    private JWTPayload payload;
    private JWTSignature signature;

    public JWT(String token) {
        this.token = token;
    }

    public static JWTEncodeResult encode(String secret, String payload) {
        return encode(secret, payload, Optional.empty(), Optional.empty());
    }

    public static JWTEncodeResult encode(String secret, String payload, String header) {
        return encode(secret, payload, Optional.of(header), Optional.empty());
    }

    public static JWTEncodeResult encode(String secret, String payload, JWTAlgorithm algorithm) {
        return encode(secret, payload, Optional.empty(), Optional.of(algorithm));
    }

    public static JWTEncodeResult encode(String secret, String payload, Optional<String> header, Optional<JWTAlgorithm> algorithm) {
        final var alg = algorithm.orElse(JWTAlgorithm.HS256);
        return JWTEncoder.encodeHeader(alg, header)
                .map(encodedHeader -> JWTEncoder.signToken(encodedHeader, payload, secret, alg))
                .orElse(new JWTEncodeResult(new JWTException(JWTExceptionType.FAILED_TO_ENCODE_HEADER)));
    }

    public void decode() {
        String[] parts = token.split("\\.");

        header = new JWTHeader();
        try {
            header.decode(new String(Base64.getUrlDecoder().decode(parts[0])));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        payload = new JWTPayload();
        try {
            payload.decode(new String(Base64.getUrlDecoder().decode(parts[1])));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static JWTDecodeResult decode(String token, String secret) {
        if (token.isBlank()) return new JWTDecodeResult(new JWTException(JWTExceptionType.NOT_ENOUGH_SEGMENTS));
        return JWTDecoder.decodeToken(token, secret);
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDRTFYbDBLSjFlMTEzYjFMd0RUMGQ1dW5xa2h2SDh0WThYZXA4V1kyVGV3In0.eyJleHAiOjE2NjU0ODUwODMsImlhdCI6MTY2NTQ4NDc4MywianRpIjoiMGJlY2RlNWQtZmY1MC00OTkyLWE5YTEtOGEyOTk4MjhlN2Y4IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMS4xNTE6OTA4MC9hdXRoL3JlYWxtcy96ZXN0aWMiLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImFjY291bnQiXSwic3ViIjoiMWE1YjBmZmEtMGE5Zi00MDkxLWI0YzktYTQ0ZTExNDUzOWViIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4iLCJzZXNzaW9uX3N0YXRlIjoiOTM1MWVhYWItZjgxZS00MTliLWJhNmUtM2FlOTE5YzVkZDlmIiwiYWNyIjoiMSIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLXplc3RpYyIsIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiYWRtaW4iOnsicm9sZXMiOlsidmlldyIsImNyZWF0ZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJtaWNyb3Byb2ZpbGUtand0IHByb2ZpbGUgcGhvbmUgZW1haWwgYWRkcmVzcyBvZmZsaW5lX2FjY2VzcyIsInNpZCI6IjkzNTFlYWFiLWY4MWUtNDE5Yi1iYTZlLTNhZTkxOWM1ZGQ5ZiIsInVwbiI6ImRlZWJlbmR1IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImFkZHJlc3MiOnt9LCJuYW1lIjoiRGVlYmVuZHUgS3VtYXIiLCJncm91cHMiOlsiZGVmYXVsdC1yb2xlcy16ZXN0aWMiLCJvZmZsaW5lX2FjY2VzcyIsImFkbWluIiwidW1hX2F1dGhvcml6YXRpb24iXSwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGVlYmVuZHUiLCJnaXZlbl9uYW1lIjoiRGVlYmVuZHUiLCJmYW1pbHlfbmFtZSI6Ikt1bWFyIiwiZW1haWwiOiJkZWViZW5kdS5rdW1hckBnbWFpbC5jb20ifQ.VDio53XZZOnGDluar74tJV04Q1g1MTfQjTZaoLJlfPOlGyVmCdTD4epJb13QdDGuGjQjDkxx8HGRj7qk_MpMag6JEDxAtECemTVtyb4RgEXD1R4xffCVjcldwasbxQLTymDjjc0aAJ2sbiAwRmJyiLW7pdD_uWKxAhf96J1NqaQjFoU8EZtqQLcwZbiid2uU14hssWQTiQWRzQfNVQHyVmwaIvAQv038uG_FUsUtGI8wJvRajVJ3xbTvyBFNJUf61J1Pgnj42SJHS457pK9_YPs-jR-FQbVRiCLCVzaGbtEdkKeVX4N7LdrBjVt1a6mhdoZtN5AFUtB0to9Tx85qhg";
        JWT jwt = new JWT(token);
        jwt.decode();
        System.out.println(jwt.getHeader().toJson().get());
        System.out.println(jwt.getPayload().toJson().get());
    }
}
