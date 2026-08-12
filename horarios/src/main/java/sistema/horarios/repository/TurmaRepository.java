package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.Turma;
import sistema.horarios.models.Turno;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma,Long> {
    List<Turma> findByTurno(Turno turno);
}
