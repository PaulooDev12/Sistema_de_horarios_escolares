package sistema.horarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.horarios.models.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
