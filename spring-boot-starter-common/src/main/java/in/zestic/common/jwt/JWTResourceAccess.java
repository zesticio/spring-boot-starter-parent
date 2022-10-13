package in.zestic.common.jwt;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
        "realm-management",
        "admin",
        "account"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JWTResourceAccess {

    @JsonProperty("realm-management")
    private JWTRealmManagement realmManagement;
    @JsonProperty("admin")
    private JWTAdmin admin;
    @JsonProperty("account")
    private JWTAccount account;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("realm-management")
    public JWTRealmManagement getRealmManagement() {
        return realmManagement;
    }

    @JsonProperty("realm-management")
    public void setRealmManagement(JWTRealmManagement realmManagement) {
        this.realmManagement = realmManagement;
    }

    @JsonProperty("admin")
    public JWTAdmin getAdmin() {
        return admin;
    }

    @JsonProperty("admin")
    public void setAdmin(JWTAdmin admin) {
        this.admin = admin;
    }

    @JsonProperty("account")
    public JWTAccount getAccount() {
        return account;
    }

    @JsonProperty("account")
    public void setAccount(JWTAccount account) {
        this.account = account;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
