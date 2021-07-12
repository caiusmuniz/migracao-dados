package br.com.caiusmuniz.migracaodados.writer;

import br.com.caiusmuniz.migracaodados.dominio.Pessoa;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaClassifierWriterConfig {
    @Bean
    public ClassifierCompositeItemWriter<Pessoa> pessoaClassifierWriter(
            JdbcBatchItemWriter<Pessoa> pessoaWriter,
            FlatFileItemWriter<Pessoa> pessoaInvalidaWriter
    ) {
        return new ClassifierCompositeItemWriterBuilder<Pessoa>()
                .classifier(classifier(pessoaWriter, pessoaInvalidaWriter))
                .build();
    }

    private Classifier<Pessoa, ItemWriter<? super Pessoa>> classifier(JdbcBatchItemWriter<Pessoa> pessoaWriter,
                                                                      FlatFileItemWriter<Pessoa> pessoaInvalidaWriter) {
        return new Classifier<Pessoa, ItemWriter<? super Pessoa>>() {
            @Override
            public ItemWriter<? super Pessoa> classify(Pessoa pessoa) {
                if (pessoa.isValid())
                    return pessoaWriter;
                else
                    return pessoaInvalidaWriter;
            }
        };
    }
}
