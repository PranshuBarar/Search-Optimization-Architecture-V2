package com.searchoptimization.reposervice.repo;

import com.searchoptimization.reposervice.entities.SharingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingTransactionRepository extends JpaRepository<SharingTransaction,Integer> {
    void deleteBySharingTransactionId(int idToBeDeleted);
}
