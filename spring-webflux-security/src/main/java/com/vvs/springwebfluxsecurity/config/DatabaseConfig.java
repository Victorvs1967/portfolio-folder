package com.vvs.springwebfluxsecurity.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.vvs.springwebfluxsecurity.model.Account;
import com.vvs.springwebfluxsecurity.repository.AccountRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages="com.vvs.springwebfluxsecurity.repository")
public class DatabaseConfig extends AbstractReactiveMongoConfiguration {

  private final Environment environment;

  public DatabaseConfig(Environment environment) {
    this.environment = environment;
  }

  @Override
  protected String getDatabaseName() {
    return "tests";
  }

  @Override
  @Bean
  @DependsOn("embeddedMongoServer")
  public MongoClient reactiveMongoClient() {
    int port = environment.getProperty("local.mongo.port", Integer.class);
    return MongoClients.create(String.format("mongodb://localhost:%d", port));
  }

  @Bean
  public CommandLineRunner insertAndView(AccountRepository accountRepository, ApplicationContext context) {
    return args -> {
      accountRepository.save(new Account("Victor", 2000.0)).subscribe();
      accountRepository.save(new Account("Nata", 1000.0)).subscribe();
      accountRepository.save(new Account("User", 500.0)).subscribe();
      accountRepository.save(new Account("Pete", 2500.0)).subscribe();
      accountRepository.save(new Account("John", 1500.0)).subscribe();
      accountRepository.findAll().subscribe(System.out::println);
    };
  }
  
}
