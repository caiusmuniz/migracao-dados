package br.com.caiusmuniz.migracaodados.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    private long id;

    private String nome;

    private String email;

    private Date dataNascimento;

    private int idade;

    public boolean isValid() {
        return !Strings.isBlank(nome) && !Strings.isBlank(email) && dataNascimento != null;
    }
}
