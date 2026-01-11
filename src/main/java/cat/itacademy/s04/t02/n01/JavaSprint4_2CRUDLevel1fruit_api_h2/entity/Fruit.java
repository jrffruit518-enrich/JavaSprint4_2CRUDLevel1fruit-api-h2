package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="fruits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private  String name;

    @Min(1)
    private  int weightInKilos;
}
