package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
