package br.ifrn.edu.ProfitFlow.models;


import br.ifrn.edu.ProfitFlow.models.enums.PessoaTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class ClienteFornecedor {
    private static final String REGEX_TELEFONE_BRASIL = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(unique = true)
    private String cpfCnpj;

    @Pattern(regexp = REGEX_TELEFONE_BRASIL, message = "Telefone inválido. Use um formato como (XX) 9XXXX-XXXX.")
    private String telefone;

    private String endereco;

    @Enumerated(EnumType.STRING)
    private PessoaTipo pessoa;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}