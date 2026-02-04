package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.CompteCaisseSearch;
import com.kabir.kabirbackend.entities.CompteCaisse;
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
public class CompteCaisseSpecification implements Specification<CompteCaisse> {

    public Specification<CompteCaisse> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("date"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("date"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("date"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("date"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("date"), dateDebut, dateFin));
                    }
                }
            }

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("compteCaisse"), commonSearchModel.isCompteCaisse()));

            query.orderBy(criteriaBuilder.desc(root.get("date")));

            return predicate;
        };
    }

    private CompteCaisseSearch compteCaisseSearch;

    @Override
    public Predicate toPredicate(Root<CompteCaisse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if(compteCaisseSearch.isSearchByDate()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("date"), compteCaisseSearch.getDateDebutSearch(), compteCaisseSearch.getDateFinSearch()));
        }

        if (compteCaisseSearch != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("compteCaisse"), compteCaisseSearch.isCompteCaisse()));
        }

        return predicate;
    }
}