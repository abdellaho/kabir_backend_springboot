package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
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

    public Specification<Absence> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateAbsence"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateAbsence"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateAbsence"), commonSearchModel.getDateDebut()));
                    }
                    if(null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateAbsence"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if(commonSearchModel.isMatin()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("matin"), commonSearchModel.isMatin()));
            }
            if (commonSearchModel.isApresMidi()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("apresMidi"), commonSearchModel.isApresMidi()));
            }
            if (null != commonSearchModel.getPersonnelId() && commonSearchModel.getPersonnelId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), commonSearchModel.getPersonnelId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateAbsence")));

            return predicate;
        };
    }

    public static Specification<Absence> searchIfExist(AbsenceDTO absenceDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (absenceDTO != null) {
                boolean bothExist = absenceDTO.isMatin() && absenceDTO.isApresMidi();

                if (absenceDTO.getDateAbsence() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateAbsence"), absenceDTO.getDateAbsence()));
                }

                if(absenceDTO.getPersonnelId() != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), absenceDTO.getPersonnelId()));
                }
                
                if(bothExist) {
                    Predicate predicate1 = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("matin"), true));
                    Predicate predicate2 = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("apresMidi"), true));
                    predicate = criteriaBuilder.or(predicate1, predicate2);
                } else {
                    if(absenceDTO.isMatin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("matin"), true));
                    }
                    if(absenceDTO.isApresMidi()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("apresMidi"), true));
                    }
                }

                if(null != absenceDTO.getId()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), absenceDTO.getId()));
                }
            }
            
            query.orderBy(criteriaBuilder.desc(root.get("dateAbsence")));

            return predicate;
        };
    }

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
