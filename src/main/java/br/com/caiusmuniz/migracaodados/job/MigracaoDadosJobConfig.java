package br.com.caiusmuniz.migracaodados.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@EnableBatchProcessing
@Configuration
public class MigracaoDadosJobConfig {
    @Autowired
    private JobBuilderFactory factory;

    @Bean
    public Job migracaoDadosJob(
            @Qualifier("pessoaStep") Step pessoaStep,
            @Qualifier("dadosBancariosStep") Step dadosBancariosStep) {
        return factory
                .get("migracaoDadosJob")
                // execucao sequencial
//                .start(pessoaStep)
//                .next(dadosBancariosStep)
                // execucao paralelo
                .start(steps(pessoaStep, dadosBancariosStep))
                .end()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Flow steps(Step pessoaStep, Step dadosBancariosStep) {
        Flow dadosBancariosFlow = new FlowBuilder<Flow>("dadosBancariosFlow")
                .start(dadosBancariosStep)
                .build();

        Flow pessoaFlow = new FlowBuilder<Flow>("pessoaFlow")
                .start(pessoaStep)
                .split(new SimpleAsyncTaskExecutor())
                .add(dadosBancariosFlow)
                .build();

        return pessoaFlow;
    }
}
