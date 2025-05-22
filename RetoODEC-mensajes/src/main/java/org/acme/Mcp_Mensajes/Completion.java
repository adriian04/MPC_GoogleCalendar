package org.acme.Mcp_Mensajes;

import java.util.List;

public class Completion extends BaseMessage {
    // Opción A
    public String origin;      // "llm" o "tool"
    public String content;     // Solo si origin == "llm"

    // Opción B
    public ToolResponse toolResponse;  // Solo si origin == "tool"

    // Clase interna para representar toolResponse
    public static class ToolResponse {
        public String tool;
        public List<Result> result;

        public static class Result {
            public String title;
            public String date;
            public String time;
        }
    }
}
  



