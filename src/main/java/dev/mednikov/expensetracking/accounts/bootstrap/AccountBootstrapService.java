package dev.mednikov.expensetracking.accounts.bootstrap;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.users.events.NewUserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountBootstrapService {

    private final Logger logger = LoggerFactory.getLogger(AccountBootstrapService.class);
    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();

    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    public AccountBootstrapService(AccountRepository accountRepository, ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.accountRepository = accountRepository;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    @EventListener
    public void onUserCreatedListener (NewUserCreatedEvent event){
        try {
            Resource resource = this.resourceLoader.getResource("classpath:bootstrap/accounts.json");
            TypeReference<List<Account>> typeReference = new TypeReference<>() {};
            List<Account> data = this.objectMapper.readValue(resource.getInputStream(), typeReference);
            List<Account> accounts = new ArrayList<>();
            for (Account item : data) {
                Account account = new Account.AccountBuilder()
                        .withId(snowflakeGenerator.next())
                        .withName(item.getName())
                        .withType(item.getType())
                        .withUser(event.getUser())
                        .build();
                accounts.add(account);
            }
            accountRepository.saveAll(accounts);
        } catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
}
