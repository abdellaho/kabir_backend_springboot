package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.entities.AchatSimple;
import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class AchatSimpleSpecification {

    public Specification<AchatSimple> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (commonSearchModel.isSearchByDate()) {
                if (null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if (commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOperation"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if (null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateDebut()));
                    }
                    if (null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if (commonSearchModel.getFournisseurId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fournisseur").get("id"), commonSearchModel.getFournisseurId()));
            }

            return predicate;
        };
    }
}