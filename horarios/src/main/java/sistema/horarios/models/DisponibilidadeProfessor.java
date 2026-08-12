package sistema.horarios.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.DayOfWeek;

@Entity
@Table(name = "Disponibilidades")
@Data
public class DisponibilidadeProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Professor professor;

    @Enumerated(EnumType.STRING)
    private DayOfWeek diaSemana;

    @Enumerated(EnumType.STRING)
    private Turno turno;
}
