package es.iesjandula.reaktor.strikes_server.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleScriptService
{

    /**
     * URL del Apps Script que devuelve respuestas
     */
    private static final String GOOGLE_SCRIPT_URL =
        "https://script.google.com/macros/s/AKfycbxBZMx9zV8tmk15DDtmf5bXA9xOzGSaZCtk65E6TorKkUoTfezQJd3PwgkaWNrsLGq05A/exec";

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Obtiene las respuestas del Google Sheet
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerRespuestas(
            String spreadsheetId,
            String sheetName)
    {
        try
        {
            String url = GOOGLE_SCRIPT_URL
                    + "?spreadsheetId=" + spreadsheetId
                    + "&sheet=" + sheetName;

            Map<String, Object>[] response =
                    restTemplate.getForObject(url, Map[].class);

            if (response == null)
            {
                return Collections.emptyList();
            }

            return Arrays.asList(response);
        }
        catch (Exception e)
        {
            log.error("Error obteniendo respuestas de Google Script", e);
            return Collections.emptyList();
        }
    }
}
