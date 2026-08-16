package sistema.horarios.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sistema.horarios.models.*;
import sistema.horarios.repository.*;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;

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
            System.out.println("Banco já populado. Pulando Seeder...");
            return;
        }

        System.out.println("Populando banco com base nas grades reais das fotos...");

        // 1. Criar Turmas (Matutino - 4 turmas representativas das fotos)
        Turma t1AM = turmaRepository.save(new Turma(null, "1AM", Turno.MATUTINO));
        Turma t1BM = turmaRepository.save(new Turma(null, "1BM", Turno.MATUTINO));
        Turma t1TEC = turmaRepository.save(new Turma(null, "1TEC", Turno.MATUTINO));
        Turma t3TEC = turmaRepository.save(new Turma(null, "3TEC", Turno.MATUTINO));

        // 2. Criar Disciplinas Comuns
        Disciplina mat = disciplinaRepository.save(new Disciplina(null, "Matemática"));
        Disciplina port = disciplinaRepository.save(new Disciplina(null, "Português"));
        Disciplina fis = disciplinaRepository.save(new Disciplina(null, "Física"));
        Disciplina qui = disciplinaRepository.save(new Disciplina(null, "Química"));
        Disciplina bio = disciplinaRepository.save(new Disciplina(null, "Biologia"));
        Disciplina hist = disciplinaRepository.save(new Disciplina(null, "História"));
        Disciplina geo = disciplinaRepository.save(new Disciplina(null, "Geografia"));
        Disciplina filo = disciplinaRepository.save(new Disciplina(null, "Filosofia"));
        Disciplina socio = disciplinaRepository.save(new Disciplina(null, "Sociologia"));
        Disciplina ing = disciplinaRepository.save(new Disciplina(null, "Inglês"));
        Disciplina esp = disciplinaRepository.save(new Disciplina(null, "Espanhol"));
        Disciplina edFis = disciplinaRepository.save(new Disciplina(null, "Ed Física"));
        Disciplina arte = disciplinaRepository.save(new Disciplina(null, "Arte"));

        // 3. Criar Disciplinas Técnicas (vistas nas fotos de 1TEC e 3TEC)
        Disciplina logica = disciplinaRepository.save(new Disciplina(null, "Lógica"));
        Disciplina aoc = disciplinaRepository.save(new Disciplina(null, "AOC"));
        Disciplina frc = disciplinaRepository.save(new Disciplina(null, "FRC"));
        Disciplina peoo = disciplinaRepository.save(new Disciplina(null, "PEOO"));
        Disciplina desSist = disciplinaRepository.save(new Disciplina(null, "Des. Sistemas"));
        Disciplina banco = disciplinaRepository.save(new Disciplina(null, "Banco de Dados"));
        Disciplina tcc = disciplinaRepository.save(new Disciplina(null, "TCC"));

        // 4. Criar Professores com Indisponibilidades Seguras (sem travar a IA)

        // Exemplo: Glaucio não dá aula na primeira hora de segunda (Indisponibilidade leve)
        Professor glaucio = new Professor(null, "Glaucio", new HashSet<>());
        for(DayOfWeek dia : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)) {
            for (int periodo = 1; periodo <= 6; periodo++) {
                glaucio.getDisponibilidades().add(new Disponibilidade(dia, periodo));
            }
        }
        glaucio = professorRepository.save(glaucio);

        // Exemplo: Patricia não atende nas últimas aulas de sexta
        Professor patricia = new Professor(null, "Patricia", new HashSet<>());

// Preenche de segunda a sexta, da 1ª à 6ª aula (liberando a agenda toda)
        for (DayOfWeek dia : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            for (int periodo = 1; periodo <= 6; periodo++) {
                patricia.getDisponibilidades().add(new Disponibilidade(dia, periodo));
            }
        }
        patricia = professorRepository.save(patricia);

        // Demais professores totalmente disponíveis para facilitar o encaixe da IA
        Professor elvis = professorRepository.save(new Professor(null, "Elvis", new HashSet<>()));
        Professor antonia = professorRepository.save(new Professor(null, "Antônia", new HashSet<>()));
        Professor joana = professorRepository.save(new Professor(null, "Joana", new HashSet<>()));
        Professor alessandro = professorRepository.save(new Professor(null, "Alessandro", new HashSet<>()));
        Professor moises = professorRepository.save(new Professor(null, "Moisés", new HashSet<>()));
        Professor wilian = professorRepository.save(new Professor(null, "Wilian", new HashSet<>()));
        Professor wendell = professorRepository.save(new Professor(null, "Wendell", new HashSet<>()));
        Professor george = professorRepository.save(new Professor(null, "George", new HashSet<>()));
        Professor andreza = professorRepository.save(new Professor(null, "Andreza", new HashSet<>()));
        Professor daniel = professorRepository.save(new Professor(null, "Daniel", new HashSet<>()));
        Professor alexandre = professorRepository.save(new Professor(null, "Alexandre", new HashSet<>()));
        Professor tiago = professorRepository.save(new Professor(null, "Tiago", new HashSet<>()));
        Professor fatima = professorRepository.save(new Professor(null, "Fátima", new HashSet<>()));
        Professor andresa = professorRepository.save(new Professor(null, "Andresa", new HashSet<>()));
        Professor sandraD = professorRepository.save(new Professor(null, "Sandra D", new HashSet<>()));
        Professor neemias = professorRepository.save(new Professor(null, "Neemias", new HashSet<>()));
        Professor thallyson = professorRepository.save(new Professor(null, "Thallyson", new HashSet<>()));

        // 5. Carga Horária (Exatamente 30 aulas distribuídas por turma para preencher toda a semana)

        // --- 1AM ---
        cargaHorariaRepository.save(new CargaHoraria(null, patricia, t1AM, mat, 5));
        cargaHorariaRepository.save(new CargaHoraria(null, antonia, t1AM, port, 4));
        cargaHorariaRepository.save(new CargaHoraria(null, alessandro, t1AM, fis, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, moises, t1AM, qui, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, wilian, t1AM, bio, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, george, t1AM, hist, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, andreza, t1AM, geo, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, daniel, t1AM, filo, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, alexandre, t1AM, socio, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, fatima, t1AM, ing, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, sandraD, t1AM, edFis, 1)); // Total: 29/30

        // --- 1BM ---
        cargaHorariaRepository.save(new CargaHoraria(null, patricia, t1BM, mat, 5));
        cargaHorariaRepository.save(new CargaHoraria(null, antonia, t1BM, port, 4));
        cargaHorariaRepository.save(new CargaHoraria(null, alessandro, t1BM, fis, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, moises, t1BM, qui, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, wilian, t1BM, bio, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, george, t1BM, hist, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, andreza, t1BM, geo, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, daniel, t1BM, filo, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, tiago, t1BM, socio, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, fatima, t1BM, ing, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, neemias, t1BM, arte, 1)); // Total: 29/30

        // --- 1TEC ---
        cargaHorariaRepository.save(new CargaHoraria(null, elvis, t1TEC, mat, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, joana, t1TEC, port, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, alessandro, t1TEC, fis, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, moises, t1TEC, qui, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, wendell, t1TEC, bio, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, george, t1TEC, hist, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, daniel, t1TEC, filo, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, fatima, t1TEC, ing, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, sandraD, t1TEC, edFis, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, thallyson, t1TEC, aoc, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, thallyson, t1TEC, logica, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, thallyson, t1TEC, frc, 2)); // Total: 28/30

        // --- 3TEC ---
        cargaHorariaRepository.save(new CargaHoraria(null, elvis, t3TEC, mat, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, joana, t3TEC, port, 3));
        cargaHorariaRepository.save(new CargaHoraria(null, alessandro, t3TEC, fis, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, moises, t3TEC, qui, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, wendell, t3TEC, bio, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, alexandre, t3TEC, socio, 2));
        cargaHorariaRepository.save(new CargaHoraria(null, neemias, t3TEC, arte, 1));
        cargaHorariaRepository.save(new CargaHoraria(null, glaucio, t3TEC, peoo, 4));
        cargaHorariaRepository.save(new CargaHoraria(null, glaucio, t3TEC, desSist, 4));
        cargaHorariaRepository.save(new CargaHoraria(null, joana, t3TEC, tcc, 3)); // Total: 28/30

        System.out.println("Seeder executado com sucesso! Acionando o motor...");
        geradorHorarioService.gerarHorariosPorTurno(Turno.MATUTINO);
    }
}