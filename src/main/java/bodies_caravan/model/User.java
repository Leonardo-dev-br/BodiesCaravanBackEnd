package bodies_caravan.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;

    @PastOrPresent(message = "A data de nascimento deve ser no passado ou presente")
    private LocalDate birthday;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    private String password;
}
