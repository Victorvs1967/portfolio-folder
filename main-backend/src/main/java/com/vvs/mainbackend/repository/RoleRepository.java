package com.vvs.mainbackend.repository;

import java.util.Optional;

import com.vvs.mainbackend.model.EnRole;
import com.vvs.mainbackend.model.Role;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, ObjectId> {
  Optional<Role> findByName(EnRole name);
}
