package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.CaisseDTO;
import com.kabir.kabirbackend.entities.Caisse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;


@Data
@Builder
public class CaisseSpecification implements Specification<Caisse> {

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
