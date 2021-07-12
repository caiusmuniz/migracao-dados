package br.com.caiusmuniz.migracaodados.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosBancarios {
    private long id;

    private long pessoaId;

    private int banco;

    private int agencia;

    private int conta;
}
