package org.acme;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.acme.Mcp_Mensajes.BaseMessage;
import org.acme.Mcp_Mensajes.Completion;
import org.acme.Mcp_Mensajes.ContextUpdate;
import org.acme.Mcp_Mensajes.TaskRequest;

@Path("/mensaje")
public class MensajeResource {

    private static final String LOG_DIR = "logs";
    private static final String HUMAN_LOG = LOG_DIR + "/fichero.log";
    private final ObjectMapper mapper = new ObjectMapper();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recibir(BaseMessage mensaje) {
        try {
            String type = mensaje.type;

            // Crear carpeta
            File carpeta = new File(LOG_DIR);
            if (!carpeta.exists()) carpeta.mkdirs();

            // Guardar en JSON Lines por tipo
            String json = mapper.writeValueAsString(mensaje);
            try (FileWriter writer = new FileWriter(LOG_DIR + "/" + type + ".jsonl", true)) {
                writer.write(json + System.lineSeparator());
            }

            // Log legible
            String resumen = generarResumen(mensaje);
            try (FileWriter writer = new FileWriter(HUMAN_LOG, true)) {
                writer.write(resumen + System.lineSeparator());
            }

            return Response.ok("Mensaje procesado: " + type).build();
        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error procesando el mensaje").build();
        }
    }

    private String generarResumen(BaseMessage mensaje) {
        String resumen = "[%s] ID: %s | Rol: %s".formatted(mensaje.type.toUpperCase(), mensaje.id, mensaje.role);
        if (mensaje instanceof TaskRequest r) {
            resumen += " | Input: " + r.input;
        } else if (mensaje instanceof ContextUpdate c) {
            resumen += " | Instrucciones: " + c.instructions;
        } else if (mensaje instanceof Completion c) {
            resumen += " | Content: " + c.content;
        }
        return resumen;
    }
}
