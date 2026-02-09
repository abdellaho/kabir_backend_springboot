package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.VilleDTO;
import com.kabir.kabirbackend.entities.Ville;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class VilleSpecification implements Specification<Ville> {

    private VilleDTO villeDTO;

    @Override
    public Predicate toPredicate(Root<Ville> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (villeDTO != null) {
            if (villeDTO.getNomVille() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("nomVille"), villeDTO.getNomVille()));
            }

            if(null != villeDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), villeDTO.getId()));
            }
        }

        query.orderBy(criteriaBuilder.asc(root.get("nomVille")));

        return predicate;
    }
}
