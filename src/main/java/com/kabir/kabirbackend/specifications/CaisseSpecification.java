package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.CaisseDTO;
import com.kabir.kabirbackend.entities.Caisse;
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
public class CaisseSpecification implements Specification<Caisse> {

    public Specification<Caisse> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOperation"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateOperation"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateOperation"), dateDebut, dateFin));
                    }
                }
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateOperation")));

            return predicate;
        };
    }

    private CaisseDTO caisseDTO;

    @Override
    public Predicate toPredicate(Root<Caisse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (caisseDTO != null) {
            if (caisseDTO.getDateOperation() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateOperation"), caisseDTO.getDateOperation()));
            }

            if(null != caisseDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), caisseDTO.getId()));
            }
        }

        return predicate;
    }
}
