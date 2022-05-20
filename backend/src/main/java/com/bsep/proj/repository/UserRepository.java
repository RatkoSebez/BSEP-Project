package com.bsep.proj.repository;

import com.bsep.proj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);
     List<User> findAllByUsername(String username);
     User findByVerificationCode(String verificationCode);
}
