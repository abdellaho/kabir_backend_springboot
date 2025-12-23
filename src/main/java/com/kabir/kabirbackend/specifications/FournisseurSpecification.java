package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
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
public class FournisseurSpecification implements Specification<Fournisseur> {

    private FournisseurDTO fournisseurDTO;

    @Override
    public Predicate toPredicate(Root<Fournisseur> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (fournisseurDTO != null) {
            if (StringUtils.isNotBlank(fournisseurDTO.getDesignation())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), fournisseurDTO.getDesignation().toLowerCase().trim()));
            }

            if (StringUtils.isNotBlank(fournisseurDTO.getTel1())) {
                Predicate tel1Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), fournisseurDTO.getTel1()),
                        criteriaBuilder.equal(root.get("tel2"), fournisseurDTO.getTel1())
                );

                predicate = criteriaBuilder.or(predicate, tel1Predicate);
            }

            if (StringUtils.isNotBlank(fournisseurDTO.getTel2())) {
                Predicate tel2Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), fournisseurDTO.getTel2()),
                        criteriaBuilder.equal(root.get("tel2"), fournisseurDTO.getTel2())
                );

                predicate = criteriaBuilder.or(predicate, tel2Predicate);
            }

            if (null != fournisseurDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), fournisseurDTO.getId()));
            }
        }

        return predicate;
    }
}
