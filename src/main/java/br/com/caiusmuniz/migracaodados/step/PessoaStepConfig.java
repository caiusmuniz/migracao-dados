package br.com.caiusmuniz.migracaodados.step;

import br.com.caiusmuniz.migracaodados.dominio.Pessoa;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaStepConfig {
    @Autowired
    private StepBuilderFactory factory;

    @Bean
    public Step pessoaStep(
            ItemReader<Pessoa> pessoaReader,
            ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter,
            FlatFileItemWriter<Pessoa> pessoaInvalidaWriter
    ) {
        return factory
                .get("pessoaStep")
                .<Pessoa, Pessoa>chunk(10000)
                .reader(pessoaReader)
                .writer(pessoaClassifierWriter)
                .stream(pessoaInvalidaWriter)
                .build();
    }
}
