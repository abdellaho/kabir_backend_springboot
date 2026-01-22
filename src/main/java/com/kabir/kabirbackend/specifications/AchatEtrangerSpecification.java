package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.entities.AchatEtranger;
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
public class AchatEtrangerSpecification implements Specification<AchatEtranger> {

    private AchatEtrangerDTO achatEtrangerDTO;

    @Override
    public Predicate toPredicate(Root<AchatEtranger> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (achatEtrangerDTO != null) {
            if(StringUtils.isNotBlank(achatEtrangerDTO.getCodeFacture())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("codeFacture")), achatEtrangerDTO.getCodeFacture().trim().toLowerCase()));
            }

            if(null != achatEtrangerDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), achatEtrangerDTO.getId()));
            }
        }

        return predicate;
    }
}