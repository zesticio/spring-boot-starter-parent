package in.zestic.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public abstract class JWTParser {

    abstract JWTParser encode(String value);
    abstract void decode(String value) throws JsonProcessingException;

    Optional<String> toJson() {
        try {
            return Optional.of(new ObjectMapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    static JWTHeader fromJson(String json) throws JWTException {
        try {
            return new ObjectMapper().readerFor(JWTHeader.class).readValue(json);
        } catch (JsonProcessingException e) {
            throw new JWTException(JWTExceptionType.FAILED_TO_PARSE_HEADER);
        }
    }

}
