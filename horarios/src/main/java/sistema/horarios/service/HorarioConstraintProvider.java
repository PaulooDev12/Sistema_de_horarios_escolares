package sistema.horarios.service;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import org.springframework.stereotype.Component;
import sistema.horarios.models.AulaAgendada;

@Component
public class HorarioConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                conflitoProfessor(constraintFactory),
                conflitoTurma(constraintFactory),
                espalharAulas(constraintFactory),
        };
    }
    private Constraint conflitoProfessor(ConstraintFactory factory) {
        return factory.forEachUniquePair(AulaAgendada.class,
                Joiners.equal(AulaAgendada::getProfessor),
                Joiners.equal(AulaAgendada::getDiaDaSemana),
                Joiners.equal(AulaAgendada::getPeriodo))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Conflito de Professor");
    }
    private Constraint conflitoTurma(ConstraintFactory factory) {
        return factory.forEachUniquePair(AulaAgendada.class,
                Joiners.equal(AulaAgendada::getTurma),
                Joiners.equal(AulaAgendada::getDiaDaSemana),
                Joiners.equal(AulaAgendada::getPeriodo))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Conflito de Turma");
    }
    private Constraint espalharAulas(ConstraintFactory factory) {
        return factory.forEachUniquePair(AulaAgendada.class,
                Joiners.equal(AulaAgendada::getTurma),
                Joiners.equal(AulaAgendada::getDisciplina),
                Joiners.equal(AulaAgendada::getDiaDaSemana))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Espalhar dia das semanas");
    }
}
