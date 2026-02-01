package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.BulletinPai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BulletinPaiRepository extends JpaRepository<BulletinPai, Long>, JpaSpecificationExecutor<BulletinPai> {

    @Query("SELECT MAX(l.numBulletin) FROM BulletinPai l")
    Optional<Integer> findMaxNum();
}