package com.agrp.dev.ukol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrp.dev.ukol.entities.UserEntity;

/**
 * @author SebelaM
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}