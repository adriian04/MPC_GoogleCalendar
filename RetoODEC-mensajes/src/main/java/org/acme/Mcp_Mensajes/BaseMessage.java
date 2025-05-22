package org.acme.Mcp_Mensajes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.EXISTING_PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TaskRequest.class, name = "taskRequest"),
    @JsonSubTypes.Type(value = ContextUpdate.class, name = "contextUpdate"),
    @JsonSubTypes.Type(value = Completion.class, name = "completion")
})
public abstract class BaseMessage {
    public String type;
    public String id;
    public String role;
}
