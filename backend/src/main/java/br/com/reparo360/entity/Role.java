package br.com.reparo360.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter                     // gera getters
@Setter                     // gera setters
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "roles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)   // ①
@ToString(onlyExplicitlyIncluded = true)            // ②
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    @EqualsAndHashCode.Include                      // ③ hash/equals consideram o id
    private Long idRole;

    @NotBlank(message = "Nome da role é obrigatório")
    @Size(max = 50, message = "Nome da role não pode exceder 50 caracteres")
    @Column(name = "nome_role", unique = true)
    @EqualsAndHashCode.Include                      // ④ e o nome (opcional, mas útil)
    @ToString.Include                               // aparece no toString
    private String nomeRole;

    @Size(max = 100, message = "Descrição não pode exceder 100 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude                               // ⑤ nunca inclua coleções LAZY
    private Set<Tecnico> tecnicos = new HashSet<>();
}
