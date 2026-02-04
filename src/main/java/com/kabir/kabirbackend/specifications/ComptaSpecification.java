package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.entities.BonSortie;
import com.kabir.kabirbackend.entities.Compta;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Data
@Builder
public class ComptaSpecification implements Specification<Compta> {

    public Specification<Compta> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateCheque"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateCheque"), dateDebut, dateFin));
                    }
                }
            }
            if(null != commonSearchModel.getPersonnelId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), commonSearchModel.getPersonnelId()));
            }
            if(null != commonSearchModel.getRepertoireId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("repertoire").get("id"), commonSearchModel.getRepertoireId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("codeSortie")));

            return predicate;
        };
    }

    public Specification<Compta> isLast(ComptaSearch comptaSearch) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("dateFin"), comptaSearch.getDateFin());
        };
    }

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