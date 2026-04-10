package es.iesjandula.reaktor.strikes_server.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.iesjandula.reaktor.strikes_server.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleScriptService
{
	@Value("${reaktor.scriptCreacionConsultaHuelga}")
	private String scriptCreacionConsultaHuelga ;

    private RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerRespuestas(String spreadsheetId, String sheetName)
    {
        try
        {
            String url = scriptCreacionConsultaHuelga + "?spreadsheetId=" + spreadsheetId  + "&sheet=" + sheetName ;

            // Leer como objeto JSON
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null)
            {
                return Collections.emptyList();
            }

            // Ajusta "data" si tu JSON usa otra clave
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data") ;

            if (data == null)
            {
                return Collections.emptyList();
            }

            return data;
        }
        catch (Exception exception)
        {
            log.error(Constants.ERR_HUELGA_DESC, exception);
            return Collections.emptyList();
        }
    }
}