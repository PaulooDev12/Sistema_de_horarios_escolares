package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.DisponibilidadeProfessor;
import sistema.horarios.models.Turno;

import java.util.List;

public interface DisponibilidadeProfessorRepository
        extends JpaRepository<DisponibilidadeProfessor, Long> {
    List<DisponibilidadeProfessor> findByTurno(Turno turno);
}
