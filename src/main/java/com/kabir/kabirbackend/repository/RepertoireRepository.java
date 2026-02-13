package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.entities.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepertoireRepository extends JpaRepository<Repertoire, Long>, JpaSpecificationExecutor<Repertoire> {

    boolean existsByDesignationIgnoreCaseAndIdNot(String designation, Long id);
    @Query("""
        select case when count(r) > 0 then true else false end
        from Repertoire r
        where (r.tel1 = :tel or r.tel2 = :tel or r.tel3 = :tel) and r.id <> :id
    """)
    boolean existsByTelAndIdNot(@Param("tel") String tel, @Param("id") Long id);

    boolean existsByIceIgnoreCaseAndIdNot(String ice, Long id);
}