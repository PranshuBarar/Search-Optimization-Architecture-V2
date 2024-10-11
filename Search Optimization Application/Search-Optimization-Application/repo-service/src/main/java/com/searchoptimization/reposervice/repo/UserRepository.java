package com.searchoptimization.reposervice.repo;


import com.searchoptimization.reposervice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByUserId(int userId);

    User findByEmail(String email);

    void deleteByEmail(String email);
}
