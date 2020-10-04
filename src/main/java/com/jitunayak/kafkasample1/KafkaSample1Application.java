package com.jitunayak.kafkasample1;

import com.jitunayak.kafkasample1.models.User;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

@SpringBootApplication
public class KafkaSample1Application {

    private final Logger logger = LoggerFactory.getLogger(KafkaSample1Application.class);
    private final TaskExecutor executor =  new SimpleAsyncTaskExecutor();

    public static void main(String[] args) {
        SpringApplication.run(KafkaSample1Application.class, args).close();
    }

    @Bean
    public SeekToCurrentErrorHandler errorHandler(KafkaTemplate<Object, Object> template){
        return new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L,2));
    }

    @Bean
    public RecordMessageConverter converter(){
        return new StringJsonMessageConverter();
    }

    @KafkaListener(id = "usergroup", topics = "userdetails")
    public void usergroup(User received)
    {
        logger.info("Received: "+received.toString());
        System.out.println("Received "+received.toString());
        this.executor.execute(()-> System.out.println("Hit enter to terminate"));
    }

    @Bean
    public NewTopic topic(){
        return new NewTopic("userdetails",1, (short) 1);
    }

    @Bean
    public ApplicationRunner runner(){
        return args-> {
            System.out.println("Hit enter to terminate");
            System.in.read();
        };
    }
}
