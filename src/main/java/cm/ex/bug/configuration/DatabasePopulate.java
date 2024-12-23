package cm.ex.bug.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePopulate {

    @Bean
    CommandLineRunner initDatabase() {
        DatabasePopulate dbp = new DatabasePopulate();
        return args -> {

//            dbp.populateAuthority(authorityRepository);
//            dbp.populateUser(userRepository, authorityRepository, passwordEncoder);

        };
    }
}
