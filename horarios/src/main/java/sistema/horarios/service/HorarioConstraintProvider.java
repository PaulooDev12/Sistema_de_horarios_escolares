package sistema.horarios.service;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import org.springframework.stereotype.Component;
import sistema.horarios.models.AulaAgendada;
import sistema.horarios.models.Professor;

@Component
public class HorarioConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                conflitoProfessor(constraintFactory),
                conflitoTurma(constraintFactory),
                espalharAulas(constraintFactory),
                professorDisponivel(constraintFactory),
                juntar2AulasSeguidas(constraintFactory),
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
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Espalhar dia das semanas");
    }
    private Constraint professorDisponivel(ConstraintFactory factory) {
        return factory.forEach(AulaAgendada.class)
                .filter(aula -> {
                    Professor professor = aula.getProfessor();
                    if(professor == null){
                        return false;
                    }
                    if(professor.getDisponibilidades() == null || professor.getDisponibilidades().isEmpty()){
                        return true;
                    }
                    boolean estaDisponivel = professor.getDisponibilidades().stream()
                            .anyMatch(disp -> disp.getDiaDaSemana().equals(aula.getDiaDaSemana())
                            && aula.getPeriodo().equals(disp.getPeriodo())
                            );
                    return !estaDisponivel;
                })
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Professor indisponivel");
    }
    private Constraint juntar2AulasSeguidas(ConstraintFactory factory) {
        return factory.forEachUniquePair(AulaAgendada.class,
        Joiners.equal(AulaAgendada::getTurma),
        Joiners.equal(AulaAgendada::getDisciplina),
        Joiners.equal(AulaAgendada::getDiaDaSemana)

        ).filter((aula1, aula2) ->
                Math.abs(aula1.getPeriodo() - aula2.getPeriodo()) > 1)
                .penalize(HardSoftScore.ofSoft(2))
                .asConstraint("Juntar de Aulas Seguidas");
    }
}
