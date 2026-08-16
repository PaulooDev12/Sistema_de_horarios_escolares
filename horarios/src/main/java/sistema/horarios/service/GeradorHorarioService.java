package sistema.horarios.service;


import ai.timefold.solver.core.api.solver.SolverManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema.horarios.models.*;
import sistema.horarios.repository.AulaAgendadaRepository;
import sistema.horarios.repository.CargaHorariaRepository;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeradorHorarioService {
    private final AulaAgendadaRepository aulaAgendadaRepository;
    private final CargaHorariaRepository cargaHorariaRepository;
    private final SolverManager<GradeHoraria, Long> solverManager;

    @Transactional
    public void gerarHorariosPorTurno(Turno turno) {
        List<AulaAgendada> aulasAntigas = aulaAgendadaRepository.findByTurma_Turno(turno);
        aulaAgendadaRepository.deleteAll(aulasAntigas);
        List<CargaHoraria> requisitos = cargaHorariaRepository.findByTurmaTurno(turno);
        List<AulaAgendada> aulasParaAgendar = new ArrayList<>();
        long idVirtual = 1L;
        for (CargaHoraria ch : requisitos) {
            for (int i = 0; i < ch.getQuantidadeAulasSemanais(); i++) {
                AulaAgendada a = new AulaAgendada();
                a.setId(idVirtual++);
                a.setTurma(ch.getTurma());
                a.setDisciplina(ch.getDisciplina());
                a.setProfessor(ch.getProfessor());
                aulasParaAgendar.add(a);
            }
        }
        List<DayOfWeek> dias = List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        );
        List<Integer> periodos = List.of(1, 2, 3, 4, 5, 6);
        GradeHoraria problema = new GradeHoraria(dias,periodos,aulasParaAgendar);
        try {
            System.out.println("IA tentando melhor solução para o turno " + turno + "...");
            GradeHoraria melhorSolucao = solverManager.solve(1L, problema).getFinalBestSolution();

            System.out.println("Horário resolvido! Score: " + melhorSolucao.getHardSoftScore());

            List<AulaAgendada> gradeFinal = melhorSolucao.getAulas();
            gradeFinal.forEach(agenda -> agenda.setId(null));

            System.out.println("Salvando " + gradeFinal.size() + " aulas no Postgres...");
            aulaAgendadaRepository.saveAll(gradeFinal);
            System.out.println("Aulas salvas com sucesso!");
            System.out.println("Grade do turno " + turno + " salva no banco de dados");


        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
