package com.kabir.kabirbackend.repository;

import com.kabir.kabirbackend.dto.DetBulletinPaiDTO;
import com.kabir.kabirbackend.entities.DetBulletinPai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetBulletinPaiRepository extends JpaRepository<DetBulletinPai, Long>, JpaSpecificationExecutor<DetBulletinPai> {

    @Query("SELECT d FROM DetBulletinPai d WHERE d.bulletinPai.id = :bulletinPaiId")
    List<DetBulletinPai> findByBulletinPaiId(@Param("bulletinPaiId") Long id);

    @Query("""
        select new com.kabir.kabirbackend.dto.DetBulletinPaiDTO(s.id, sum(d.prixVente), sum(d.qteLivrer), s.pvttc, sum(d.remiseLivraison),
            sum(d.montantProduit), sum(d.beneficeDH), s.commission, false)
        from DetLivraison d
        join d.stock s
        join d.livraison l
        where ((:sansMontant = true and d.montantProduit = 0 and l.mantantBL = 0) or (:sansMontant = false and d.montantProduit > 0))
            and l.dateBl between :start and :end
            and l.personnel.id = :repId
            and (:livraisonId is null or l.id = :livraisonId)
        group by s.id, s.pvttc, s.commission
        order by s.id
    """)
    List<DetBulletinPaiDTO> getDetBulletinPai(@Param("start") LocalDate start,  @Param("end") LocalDate end, @Param("repId") Long repId, @Param("sansMontant") boolean sansMontant, @Param("livraisonId") Long livraisonId);

}