package sistema.horarios.models;


import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@PlanningSolution
public class GradeHoraria {

    @ValueRangeProvider(id = "diasRange")
    private List<DayOfWeek> diasDisponiveis;

    @ValueRangeProvider(id = "periodosRange")
    private List<Integer> periodosDisponiveis;

    @PlanningEntityCollectionProperty
    private List<AulaAgendada> aulas = new ArrayList<>();

    @PlanningScore
    private HardSoftScore hardSoftScore;

    public GradeHoraria(List<DayOfWeek> diasDisponiveis, List<Integer> periodosDisponiveis, List<AulaAgendada> aulas) {
        this.diasDisponiveis = diasDisponiveis;
        this.periodosDisponiveis = periodosDisponiveis;
        this.aulas = aulas != null ? aulas : new ArrayList<>();
    }

}

