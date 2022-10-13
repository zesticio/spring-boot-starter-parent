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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"alg", "kid"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JWTHeader extends JWTParser {

    @JsonProperty("alg")
    private JWTAlgorithm alg;
    @JsonRawValue
    @JsonProperty("kid")
    private String extraHeader;
    @JsonProperty("typ")
    private String type;

    JWTHeader(JWTAlgorithm alg) {
        this.alg = alg;
        this.type = "JWT";
    }

    JWTHeader(JWTAlgorithm alg, String header) {
        this.alg = alg;
        this.type = "JWT";
        this.extraHeader = header;
    }

    public JWTParser encode(String value) {
        return this;
    }

    public void decode(String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JWTHeader header = mapper.readValue(value, JWTHeader.class);
        this.alg = header.alg;
        this.extraHeader = header.getExtraHeader();
        this.type = header.type;
    }

}
