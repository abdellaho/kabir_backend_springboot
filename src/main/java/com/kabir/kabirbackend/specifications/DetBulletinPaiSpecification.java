package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.entities.DetLivraison;
import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Stock;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DetBulletinPaiSpecification {

    public static Specification<DetLivraison> findForBulletinPai(LocalDate dateDebut, LocalDate dateFin, Long repId, boolean sansMontant) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Joins
            Join<DetLivraison, Stock> stockJoin = root.join("stock", JoinType.INNER);
            Join<DetLivraison, Livraison> livraisonJoin = root.join("livraison", JoinType.INNER);
            Join<Livraison, Personnel> personnelJoin = livraisonJoin.join("personnel", JoinType.INNER);

            // Filtres obligatoires
            if (dateDebut != null && dateFin != null) {
                predicates.add(cb.between(livraisonJoin.get("dateBl"), dateDebut, dateFin));
            }

            if (repId != null) {
                predicates.add(cb.equal(personnelJoin.get("id"), repId));
            }

            // Filtre conditionnel
            if (sansMontant) {
                predicates.add(cb.equal(root.get("montantProduit"), BigDecimal.ZERO));
            } else {
                predicates.add(cb.notEqual(root.get("montantProduit"), BigDecimal.ZERO));
            }

            // Group by (pour les agrégations)
            query.groupBy(stockJoin.get("id"), stockJoin.get("pvttc"), stockJoin.get("commission"));

            query.orderBy(cb.asc(stockJoin.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Specifications réutilisables individuelles
    public static Specification<DetLivraison> hasDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            Join<DetLivraison, Livraison> livraison = root.join("livraison");
            return cb.between(livraison.get("dateBl"), start, end);
        };
    }

    public static Specification<DetLivraison> hasRepresentant(Long repId) {
        return (root, query, cb) -> {
            Join<DetLivraison, Livraison> livraison = root.join("livraison");
            return cb.equal(livraison.get("personnel").get("id"), repId);
        };
    }

    public static Specification<DetLivraison> hasMontantZero() {
        return (root, query, cb) -> cb.equal(root.get("montantProduit"), BigDecimal.ZERO);
    }

    public static Specification<DetLivraison> hasStock(Long stockId) {
        return (root, query, cb) -> cb.equal(root.get("stock").get("id"), stockId);
    }

    public static Specification<DetLivraison> hasClient(Long clientId) {
        return (root, query, cb) -> {
            Join<DetLivraison, Livraison> livraison = root.join("livraison");
            return cb.equal(livraison.get("client").get("id"), clientId);
        };
    }
}
