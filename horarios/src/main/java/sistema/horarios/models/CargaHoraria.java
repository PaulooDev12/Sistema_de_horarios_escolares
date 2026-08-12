package sistema.horarios.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CargaHoraria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargaHoraria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Professor professor;

    @ManyToOne
    private Turma turma;

    @ManyToOne
    private Disciplina disciplina;

    private int quantidadeAulasSemanais;
}
