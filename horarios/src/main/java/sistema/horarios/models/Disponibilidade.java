package sistema.horarios.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidade {
    @Enumerated(EnumType.STRING)
    private DayOfWeek diaDaSemana;

    private Integer periodo;

}
