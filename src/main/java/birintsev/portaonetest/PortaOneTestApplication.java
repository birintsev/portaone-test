package birintsev.portaonetest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import picocli.CommandLine;

@SpringBootApplication
public class PortaOneTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortaOneTestApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(Command command) {
        return args -> new CommandLine(command).execute(args);
    }
}
