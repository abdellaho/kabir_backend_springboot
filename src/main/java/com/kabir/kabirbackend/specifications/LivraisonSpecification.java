package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.Livraison;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;


@Data
@Builder
public class LivraisonSpecification implements Specification<Livraison> {

    private LivraisonDTO livraisonDTO;

    @Override
    public Predicate toPredicate(Root<Livraison> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (livraisonDTO != null) {
            if (StringUtils.isNotBlank(livraisonDTO.getCodeBl())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("codeBl")), livraisonDTO.getCodeBl().toLowerCase().trim()));
            }

            if (null != livraisonDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), livraisonDTO.getId()));
            }
        }

        return predicate;
    }
}
