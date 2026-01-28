package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.entities.Compta;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class ComptaSpecification implements Specification<Compta> {

    private ComptaSearch comptaSearch;

    @Override
    public Predicate toPredicate(Root<Compta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (comptaSearch != null) {
            if (comptaSearch.isSearchByDate()) {
                if(comptaSearch.getDateDebut() != null && comptaSearch.getDateFin() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), comptaSearch.getDateDebut()));
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateFin"), comptaSearch.getDateFin()));
                } else if(comptaSearch.getDateDebut() != null || comptaSearch.getDateFin() != null){
                    if(comptaSearch.getDateDebut() != null) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), comptaSearch.getDateDebut()));
                    }
                    if(comptaSearch.getDateFin() != null) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateFin"), comptaSearch.getDateFin()));
                    }
                }
            }

            if(null != comptaSearch.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), comptaSearch.getId()));
            }

            if(comptaSearch.getRepertoireId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("repertoire").get("id"), comptaSearch.getRepertoireId()));
            }
        }

        return predicate;
    }
}