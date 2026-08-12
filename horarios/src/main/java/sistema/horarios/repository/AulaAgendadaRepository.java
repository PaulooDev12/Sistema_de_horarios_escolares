package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.AulaAgendada;
import sistema.horarios.models.Turno;

import java.util.List;

public interface AulaAgendadaRepository extends JpaRepository<AulaAgendada, Long> {
    void deleteByTurmaTurno(Turno turno);
    List<AulaAgendada> findByTurma_TurnoOrderByDiaDaSemanaAscPeriodoAsc(Turno turno);
    List<AulaAgendada> findByTurmaIdOrderByDiaDaSemanaAscPeriodoAsc(Long id);
}
