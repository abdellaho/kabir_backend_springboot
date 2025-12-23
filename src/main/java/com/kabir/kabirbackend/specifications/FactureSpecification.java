package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.entities.Facture;
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
public class FactureSpecification implements Specification<Facture> {

    private FactureDTO factureDTO;

    @Override
    public Predicate toPredicate(Root<Facture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (factureDTO != null) {
            if (StringUtils.isNotBlank(factureDTO.getCodeBF())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codeBF"), factureDTO.getCodeBF()));
            }

            if(null != factureDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), factureDTO.getId()));
            }
        }
        return predicate;

    }
}
