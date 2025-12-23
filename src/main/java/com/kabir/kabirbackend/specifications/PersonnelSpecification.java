package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.entities.Personnel;
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
public class PersonnelSpecification implements Specification<Personnel> {

    private PersonnelDTO personnelDTO;

    @Override
    public Predicate toPredicate(Root<Personnel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (personnelDTO != null) {
            if (StringUtils.isNotBlank(personnelDTO.getDesignation())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), personnelDTO.getDesignation().toLowerCase().trim()));
            }

            if (StringUtils.isNotBlank(personnelDTO.getTel1())) {
                Predicate tel1Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), personnelDTO.getTel1()),
                        criteriaBuilder.equal(root.get("tel2"), personnelDTO.getTel1())
                );

                predicate = criteriaBuilder.or(predicate, tel1Predicate);
            }

            if (StringUtils.isNotBlank(personnelDTO.getTel2())) {
                Predicate tel2Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), personnelDTO.getTel2()),
                        criteriaBuilder.equal(root.get("tel2"), personnelDTO.getTel2())
                );

                predicate = criteriaBuilder.or(predicate, tel2Predicate);
            }

            if (null != personnelDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), personnelDTO.getId()));
            }
        }

        return predicate;
    }
}
