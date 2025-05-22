package org.acme.Mcp_Mensajes;

import java.util.List;

public class ContextUpdate extends BaseMessage {
    public List<Tool> tools;
    public String instructions;
    public User user;

    public static class Tool {
        public String name;
        public String description;
        public Parameters parameters;

        public static class Parameters {
            public String date;
            public String title;
            public String time;
        }
    }

    public static class User {
        public String id;
        public String name;
    }
}
