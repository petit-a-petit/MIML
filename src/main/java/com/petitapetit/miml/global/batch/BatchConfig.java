package com.petitapetit.miml.global.batch;

import com.petitapetit.miml.domain.track.entity.Track;
import com.petitapetit.miml.domain.track.dto.TrackDto;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;
    private final TrackItemProcessor taskItemProcessor;

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public FlatFileItemReader<TrackDto> reader() {
        return new FlatFileItemReaderBuilder<TrackDto>()
                .name("trackItemReader")
                .resource(new ClassPathResource("static/regional-kr-weekly-2023-11-09.csv"))
                .linesToSkip(1)
                .delimited()
                .names(new String[]{"rank", "uri", "artist_names", "track_name", "source","peak_rank","previous_rank","weeks_on_chart","streams"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TrackDto>() {{
                    setTargetType(TrackDto.class);
                }})
                .build();
    }

    @Bean
    public JpaItemWriter<Track> writer() {
        JpaItemWriter<Track> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step importTrackStep(JpaItemWriter<Track> writer) {
        return stepBuilderFactory.get("importTrackStep")
                .<TrackDto, Track>chunk(10)
                .reader(reader())
                .processor(taskItemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importTrackJob(Step importTrackStep) {
        return jobBuilderFactory.get("importTrackJob")
                .incrementer(new RunIdIncrementer())
                .flow(importTrackStep)
                .end()
                .build();
    }
}

