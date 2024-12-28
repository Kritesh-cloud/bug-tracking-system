package cm.ex.bug.configuration;

import cm.ex.bug.configuration.data.DataHolderData;
import cm.ex.bug.entity.Authority;
import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.repository.AuthorityRepository;
import cm.ex.bug.repository.DataHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePopulate {

    @Autowired
    private DataHolderRepository dataHolderRepository;

    @Autowired
    private AuthorityRepository authorityRepository;


    @Bean
    CommandLineRunner initDatabase() {
        DataHolderData dataHolderData = new DataHolderData();
        return args -> {
            if (dataHolderRepository.count() == 0) {
                for (Authority authority : dataHolderData.getAuthorityList()) {
                    System.out.println("authority: "+authority);
//                    authorityRepository.save(authority);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderStatus()) {
                    System.out.println("dataHolder: "+dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderSeverity()) {
                    System.out.println("dataHolder: "+dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderPriority()) {
                    System.out.println("dataHolder: "+dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
                for (DataHolder dataHolder : dataHolderData.getDataHolderRole()) {
                    System.out.println("dataHolder: "+dataHolder);
                    dataHolderRepository.save(dataHolder);
                }
            }
        };
    }

}
