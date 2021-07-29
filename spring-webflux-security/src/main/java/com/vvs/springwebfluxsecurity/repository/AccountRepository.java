package com.vvs.springwebfluxsecurity.repository;

import com.vvs.springwebfluxsecurity.model.Account;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {  
}
