package sistema.horarios.service;


import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sistema.horarios.models.AulaAgendada;
import sistema.horarios.models.GradeHoraria;

import java.time.Duration;

@Configuration
public class TimeFoldConfig{

    @Bean
    public SolverManager<GradeHoraria, Long>  solverManager(){
        SolverConfig solverConfig = new SolverConfig()
                .withSolutionClass(GradeHoraria.class)
                .withEntityClasses(AulaAgendada.class)
                .withConstraintProviderClass(HorarioConstraintProvider.class)
                .withTerminationConfig(new TerminationConfig()
                        .withSpentLimit(Duration.ofSeconds(10))
                );
        return SolverManager.create(SolverFactory.create(solverConfig));
    }
}
