package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.DetAchatSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetAchatSimpleRepository extends JpaRepository<DetAchatSimple, Long>, JpaSpecificationExecutor<DetAchatSimple> {

    @Query("SELECT dsd FROM DetAchatSimple dsd WHERE dsd.achatSimple.id = :achatSimpleId")
    List<DetAchatSimple> findAllByAchatSimpleId(@Param("achatSimpleId") Long achatSimpleId);
}