package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.CargaHoraria;
import sistema.horarios.models.Turno;

import java.util.List;

public interface CargaHorariaRepository extends JpaRepository<CargaHoraria, Long> {
    List<CargaHoraria> findByTurmaTurno(Turno turno);
}
