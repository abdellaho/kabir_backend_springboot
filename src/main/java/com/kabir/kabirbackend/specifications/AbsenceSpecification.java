package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.entities.Absence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;


@Data
@Builder
public class AbsenceSpecification implements Specification<Absence> {

    private AbsenceDTO absenceDTO;

    @Override
    public Predicate toPredicate(Root<Absence> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (absenceDTO != null) {
            if (absenceDTO.getDateAbsence() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateAbsence"), absenceDTO.getDateAbsence()));
            }

            if(absenceDTO.getPersonnelId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), absenceDTO.getPersonnelId()));
            }

            if(null != absenceDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), absenceDTO.getId()));
            }
        }

        return predicate;
    }
}
