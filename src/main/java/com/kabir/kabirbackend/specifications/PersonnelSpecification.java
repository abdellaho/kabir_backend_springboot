package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.entities.Absence;
import com.kabir.kabirbackend.entities.Personnel;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PersonnelSpecification implements Specification<Personnel> {

    private PersonnelDTO personnelDTO;

    public static Specification<Personnel> searchBySupprimerOrArchiver(PersonnelDTO personnelDTO) {
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            return criteriaBuilder.and(
                criteriaBuilder.equal(root.get("supprimer"), personnelDTO.isSupprimer()),
                criteriaBuilder.equal(root.get("archiver"), personnelDTO.isArchiver())
            );
        };
    }

    public static Specification<Personnel> searchBySupprimerOrArchiverExceptAdmin(PersonnelDTO personnelDTO) {
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            return criteriaBuilder.and(
                    criteriaBuilder.notEqual(root.get("typePersonnel"), 1),
                    criteriaBuilder.equal(root.get("supprimer"), personnelDTO.isSupprimer()),
                    criteriaBuilder.equal(root.get("archiver"), personnelDTO.isArchiver())
            );
        };
    }

    public static Specification<Personnel> notInAbsenceAtDate(AbsenceDTO absenceDTO) {
        return (root, query, cb) -> {
            // typePersonnel != 1
            Predicate typePredicate = cb.notEqual(root.get("typePersonnel"), 1);

            // subquery
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Absence> absence = subquery.from(Absence.class);

            List<Predicate> subPredicates = new ArrayList<>();
            subPredicates.add(cb.equal(absence.get("personnel").get("id"), root.get("id")));
            subPredicates.add(cb.equal(absence.get("dateAbsence"), absenceDTO.getDateAbsence()));

            // exclude current personnel inside subquery
            if (absenceDTO.getPersonnelId() != null) {
                subPredicates.add(cb.notEqual(absence.get("personnel").get("id"), absenceDTO.getPersonnelId()));
            }

            subquery.select(absence.get("personnel").get("id")).where(subPredicates.toArray(new Predicate[0]));

            Predicate notExistsPredicate = cb.not(cb.exists(subquery));

            return cb.and(typePredicate, notExistsPredicate);
        };
    }


    @Override
    public Predicate toPredicate(Root<Personnel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (personnelDTO != null) {
            if (StringUtils.isNotBlank(personnelDTO.getDesignation())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), personnelDTO.getDesignation().toLowerCase().trim()));
            }

            if (StringUtils.isNotBlank(personnelDTO.getEmail())) {
                predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("email")), personnelDTO.getEmail().toLowerCase().trim()));
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

        query.orderBy(criteriaBuilder.asc(root.get("designation")));

        return predicate;
    }


}
