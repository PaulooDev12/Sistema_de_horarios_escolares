package sistema.horarios.models;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;

@Entity
@Table(name = "Aulas")
@PlanningEntity
@Data
public class AulaAgendada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PlanningId
    private Long id;

    @ManyToOne
    private Turma turma;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Disciplina disciplina;

    @PlanningVariable(valueRangeProviderRefs = "diasRange")
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaDaSemana;

    @PlanningVariable(valueRangeProviderRefs = "periodosRange")
    private Integer periodo;
}
