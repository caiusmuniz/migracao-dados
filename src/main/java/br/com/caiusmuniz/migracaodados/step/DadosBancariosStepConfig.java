package br.com.caiusmuniz.migracaodados.step;

import br.com.caiusmuniz.migracaodados.dominio.DadosBancarios;
import br.com.caiusmuniz.migracaodados.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DadosBancariosStepConfig {
    @Autowired
    private StepBuilderFactory factory;

    @Bean
    public Step dadosBancariosStep(
            ItemReader<DadosBancarios> dadosBancariosReader,
            ItemWriter<DadosBancarios> dadosBancariosWriter
    ) {
        return factory
                .get("dadosBancariosStep")
                .<DadosBancarios, DadosBancarios>chunk(10000)
                .reader(dadosBancariosReader)
                .writer(dadosBancariosWriter)
                .build();
    }
}
