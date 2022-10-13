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
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonPropertyOrder({
        "exp",
        "iat",
        "jti",
        "iss",
        "aud",
        "sub",
        "typ",
        "azp",
        "session_state",
        "acr",
        "realm_access",
        "resource_access",
        "scope",
        "sid",
        "upn",
        "email_verified",
        "address",
        "name",
        "groups",
        "preferred_username",
        "given_name",
        "family_name",
        "email"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JWTPayload extends JWTParser {
    @JsonProperty("exp")
    private Integer exp;
    @JsonProperty("iat")
    private Integer iat;
    @JsonProperty("jti")
    private String jti;
    @JsonProperty("iss")
    private String iss;
    @JsonProperty("aud")
    private List<String> aud = null;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("typ")
    private String typ;
    @JsonProperty("azp")
    private String azp;
    @JsonProperty("session_state")
    private String sessionState;
    @JsonProperty("acr")
    private String acr;
    @JsonProperty("realm_access")
    private JWTRealmAccess realmAccess;
    @JsonProperty("resource_access")
    private JWTResourceAccess resourceAccess;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("sid")
    private String sid;
    @JsonProperty("upn")
    private String upn;
    @JsonProperty("email_verified")
    private Boolean emailVerified;
    @JsonProperty("address")
    private JWTAddress address;
    @JsonProperty("name")
    private String name;
    @JsonProperty("groups")
    private List<String> groups = null;
    @JsonProperty("preferred_username")
    private String preferredUsername;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    @Override
    JWTParser encode(String value) {
        return null;
    }

    @Override
    void decode(String value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JWTPayload payload = mapper.readValue(value, JWTPayload.class);
        this.acr = payload.getAcr();
        this.address = payload.getAddress();
        this.aud = payload.getAud();
        this.azp = payload.getAzp();
        this.email = payload.getEmail();
        this.emailVerified = payload.getEmailVerified();
        this.exp = payload.getExp();
        this.familyName = payload.getFamilyName();
        this.givenName = payload.getGivenName();
        this.groups = payload.getGroups();
        this.iat = payload.getIat();
        this.iss = payload.getIss();
        this.jti = payload.getJti();
        this.name = payload.getName();
        this.preferredUsername = payload.getPreferredUsername();
        this.realmAccess = payload.getRealmAccess();
        this.resourceAccess = payload.getResourceAccess();
        this.scope = payload.getScope();
        this.sessionState = payload.getSessionState();
        this.sid = payload.getSid();
        this.sub = payload.getSub();
        this.typ = payload.getTyp();
        this.upn = payload.getUpn();
    }
}
