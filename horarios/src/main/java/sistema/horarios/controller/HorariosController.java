package sistema.horarios.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.horarios.models.AulaAgendada;
import sistema.horarios.models.Turma;
import sistema.horarios.models.Turno;
import sistema.horarios.repository.AulaAgendadaRepository;
import sistema.horarios.service.GeradorHorarioService;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorariosController {

    private final AulaAgendadaRepository aulaAgendadaRepo;
    private final GeradorHorarioService geradorHorarioService;

    public HorariosController(AulaAgendadaRepository aulaAgendadaRepo,
                              GeradorHorarioService geradorHorarioService) {
        this.aulaAgendadaRepo = aulaAgendadaRepo;
        this.geradorHorarioService = geradorHorarioService;
    }
    @GetMapping("/grades")
    public ResponseEntity<List<AulaAgendada>> getGrades(){
        return ResponseEntity.ok(aulaAgendadaRepo.findAll());
    }
    @GetMapping("/buscar-turno/{turno}")
    public ResponseEntity<List<AulaAgendada>> getTurma(@PathVariable Turno turno){
        List<AulaAgendada> horarioDoTurno = aulaAgendadaRepo.findByTurma_TurnoOrderByDiaDaSemanaAscPeriodoAsc(turno);
        if(horarioDoTurno.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horarioDoTurno);
    }
    @GetMapping("buscar-turma/{id}")
    public ResponseEntity<List<AulaAgendada>> getAula(@PathVariable Long id){
        List<AulaAgendada> horarioDaTurma = aulaAgendadaRepo.findByTurmaIdOrderByDiaDaSemanaAscPeriodoAsc(id);
        if(horarioDaTurma.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horarioDaTurma);
    }
    @PostMapping("/gerar/{turno}")
    public ResponseEntity<String> gerarHorarios(@PathVariable("turno") Turno turno){
        try{
            geradorHorarioService.gerarHorariosPorTurno(turno);
            return ResponseEntity.ok("Horarios gerados com sucesso");
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Não foi possivel gerar os horarios. erro: " + e.getMessage());
        }
    }
}
