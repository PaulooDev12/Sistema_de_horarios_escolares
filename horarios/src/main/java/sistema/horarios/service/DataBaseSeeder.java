package sistema.horarios.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sistema.horarios.models.*;
import sistema.horarios.repository.*;

@Component
@RequiredArgsConstructor
public class DataBaseSeeder implements CommandLineRunner {

    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final TurmaRepository turmaRepository;
    private final CargaHorariaRepository cargaHorariaRepository;
    private final GeradorHorarioService geradorHorarioService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (turmaRepository.count() > 0) {
            System.out.println("Banco já populado. Pulando o Seeder...");

            return;
        }

        System.out.println("Populando banco com carga horária pesada para forçar a semana cheia...");

        // 1. Criar Professores
        Professor p1 = professorRepository.save(new Professor(null, "Carlos (Matemática)"));
        Professor p2 = professorRepository.save(new Professor(null, "Ana (Português)"));
        Professor p3 = professorRepository.save(new Professor(null, "Marcos (História/Geografia)"));
        Professor p4 = professorRepository.save(new Professor(null, "Juliana (Ciências)"));
        Professor p5 = professorRepository.save(new Professor(null, "Roberto (Inglês/Artes)"));

        // 2. Criar Disciplinas
        Disciplina mat = disciplinaRepository.save(new Disciplina(null, "Matemática"));
        Disciplina port = disciplinaRepository.save(new Disciplina(null, "Português"));
        Disciplina hist = disciplinaRepository.save(new Disciplina(null, "História"));
        Disciplina geo = disciplinaRepository.save(new Disciplina(null, "Geografia"));
        Disciplina cien = disciplinaRepository.save(new Disciplina(null, "Ciências"));
        Disciplina ing = disciplinaRepository.save(new Disciplina(null, "Inglês"));


        Turma turmaA = turmaRepository.save(new Turma(null, "1º Ano A", Turno.MATUTINO));
        Turma turmaB = turmaRepository.save(new Turma(null, "2º Ano A", Turno.MATUTINO));


        cargaHorariaRepository.save(new CargaHoraria(null, p1, turmaA, mat, 6));  // 6 aulas de Matemática
        cargaHorariaRepository.save(new CargaHoraria(null, p2, turmaA, port, 6)); // 6 aulas de Português
        cargaHorariaRepository.save(new CargaHoraria(null, p3, turmaA, hist, 4)); // 4 aulas de História
        cargaHorariaRepository.save(new CargaHoraria(null, p3, turmaA, geo, 4));  // 4 aulas de Geografia
        cargaHorariaRepository.save(new CargaHoraria(null, p4, turmaA, cien, 5)); // 5 aulas de Ciências
        cargaHorariaRepository.save(new CargaHoraria(null, p5, turmaA, ing, 3));  // 3 aulas de Inglês


        cargaHorariaRepository.save(new CargaHoraria(null, p2, turmaB, port, 6)); // 6 aulas de Português
        cargaHorariaRepository.save(new CargaHoraria(null, p1, turmaB, mat, 6));  // 6 aulas de Matemática
        cargaHorariaRepository.save(new CargaHoraria(null, p4, turmaB, cien, 5)); // 5 aulas de Ciências
        cargaHorariaRepository.save(new CargaHoraria(null, p3, turmaB, hist, 4)); // 4 aulas de História
        cargaHorariaRepository.save(new CargaHoraria(null, p3, turmaB, geo, 4));  // 4 aulas de Geografia
        cargaHorariaRepository.save(new CargaHoraria(null, p5, turmaB, ing, 3));  // 3 aulas de Inglês

        System.out.println("Seeder concluído com sucesso! Acionando a IA...");

        geradorHorarioService.gerarHorariosPorTurno(Turno.MATUTINO);

        System.out.println("Processo de IA finalizado. Verifique as grades!");
    }
}