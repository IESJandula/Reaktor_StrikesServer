package es.iesjandula.reaktor.strikes_server.dtos;

import lombok.Data;

@Data
public class AlumnoHuelgaRequestDto
{
    private String titulo;
    private String email;
    private String participa;
}
