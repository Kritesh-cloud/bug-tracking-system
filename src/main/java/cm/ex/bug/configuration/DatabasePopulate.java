package cm.ex.bug.configuration;

import cm.ex.bug.configuration.data.DataHolderData;
import cm.ex.bug.entity.Authority;
import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.User;
import cm.ex.bug.repository.AuthorityRepository;
import cm.ex.bug.repository.DataHolderRepository;
import cm.ex.bug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Configuration
public class DatabasePopulate {

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initDatabase() {
        DataHolderData dataHolderData = new DataHolderData();
        return args -> {

            if (authorityRepository.count() == 0) {
                for (Authority authority : dataHolderData.getAuthorityList()) {
                    System.out.println("authority: " + authority);
                    authorityRepository.save(authority);
                }
            }

            if (dataHolderRepository.count() == 0) {

                for (DataHolder dataHolder : dataHolderData.getDataHolderStatus()) {
                    System.out.println("dataHolder: " + dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderSeverity()) {
                    System.out.println("dataHolder: " + dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderPriority()) {
                    System.out.println("dataHolder: " + dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderRole()) {
                    System.out.println("dataHolder: " + dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
            }

            if (userRepository.count() == 0) {

                Optional<Authority> authorityAdmin = authorityRepository.findByAuthority("admin");
                Optional<Authority> authorityModerator = authorityRepository.findByAuthority("moderator");
                Optional<Authority> authorityUser = authorityRepository.findByAuthority("user");

                Set<Authority> authoritySet = new HashSet<>();
                authoritySet.add(authorityAdmin.get());
                authoritySet.add(authorityModerator.get());
                authoritySet.add(authorityUser.get());
                User admin = dataHolderData.getAdmin();
//                admin.setAuthoritySet(authoritySet);
                userRepository.save(admin);
                System.out.println("admin saved");

                authoritySet.clear();
                authoritySet.add(authorityModerator.get());
                authoritySet.add(authorityUser.get());
                User moderator = dataHolderData.getAdmin();
//                admin.setAuthoritySet(authoritySet);
                userRepository.save(moderator);
                System.out.println("moderator saved");

                authoritySet.clear();
                authoritySet.add(authorityUser.get());
                for (User user : dataHolderData.getUserList()) {
//                    user.setAuthoritySet(authoritySet);
                    userRepository.save(user);
                    System.out.println("user saved: " + user.getName());
                }

                System.out.println("\nAll 12 user saved\n");
            }
        };
    }

}
