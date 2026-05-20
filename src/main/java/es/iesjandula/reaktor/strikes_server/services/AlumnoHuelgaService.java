package es.iesjandula.reaktor.strikes_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.strikes_server.models.Alumno;
import es.iesjandula.reaktor.strikes_server.models.AlumnoHuelga;
import es.iesjandula.reaktor.strikes_server.models.Huelga;
import es.iesjandula.reaktor.strikes_server.models.ids.AlumnoHuelgaId;
import es.iesjandula.reaktor.strikes_server.repository.IAlumnoHuelgaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlumnoHuelgaService
{
    @Autowired
    private IAlumnoHuelgaRepository alumnoHuelgaRepository ;
    
    public void registrarOActualizar(Huelga huelga, Alumno alumno, boolean participa) 
    {
    	AlumnoHuelga alumnoHuelgaInscrito = alumnoHuelgaRepository.findByAlumnoHuelgaIdEmailAndAlumnoHuelgaIdTitulo(
    	            alumno.getEmail(),
    	            huelga.getTitulo()) ;

	    if (alumnoHuelgaInscrito == null)
	    {
	    	log.info("CREANDO NUEVO REGISTRO → {} {}", alumno.getEmail(), huelga.getTitulo());
	        AlumnoHuelga alumnoHuelgaNuevo = new AlumnoHuelga() ;

	        AlumnoHuelgaId alumnoHuelgaId = new AlumnoHuelgaId(alumno.getEmail(),huelga.getTitulo()) ;

	        alumnoHuelgaNuevo.setAlumnoHuelgaId(alumnoHuelgaId) ;
	        alumnoHuelgaNuevo.setAlumno(alumno) ;
	        alumnoHuelgaNuevo.setHuelga(huelga) ;
	        alumnoHuelgaNuevo.setParticipa(participa) ;
	        alumnoHuelgaRepository.saveAndFlush(alumnoHuelgaNuevo) ;
	    }
	    else
	    {
	        if (alumnoHuelgaInscrito.getParticipa() != participa)
	        {
	        	alumnoHuelgaInscrito.setParticipa(participa);
	            alumnoHuelgaRepository.save(alumnoHuelgaInscrito);
	        }
	    }
    }
}
