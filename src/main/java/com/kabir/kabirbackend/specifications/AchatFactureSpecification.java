package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.entities.AchatFacture;
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
public class AchatFactureSpecification implements Specification<AchatFacture> {

    private AchatFactureDTO achatFactureDTO;

    @Override
    public Predicate toPredicate(Root<AchatFacture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (achatFactureDTO != null) {
            if(StringUtils.isNotBlank(achatFactureDTO.getNumeroFacExterne())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("numeroFacExterne")), achatFactureDTO.getNumeroFacExterne().trim().toLowerCase()));
            }

            if(null != achatFactureDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), achatFactureDTO.getId()));
            }
        }

        return predicate;
    }
}
