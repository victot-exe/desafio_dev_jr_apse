package com.victot.desafio_dev_jr_apse.repository;

import com.victot.desafio_dev_jr_apse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
