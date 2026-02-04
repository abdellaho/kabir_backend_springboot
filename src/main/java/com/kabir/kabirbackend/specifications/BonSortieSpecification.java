package com.kabir.kabirbackend.specifications;


import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.entities.BonSortie;
import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Data
@Builder
public class BonSortieSpecification {

    public Specification<BonSortie> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateDebut"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateDebut"), dateDebut, dateFin));
                    }
                }
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateDebut")));

            return predicate;
        };
    }
}
