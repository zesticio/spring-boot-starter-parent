package in.zestic.common.jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
        "roles"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JWTRealmManagement {
    @JsonProperty("roles")
    private List<String> roles = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
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
