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

    public static Specification<Personnel> notInAbsenceAtDate1(AbsenceDTO absenceDTO) {
        return (root, query, cb) -> {
            // typePersonnel != 1
            Predicate typePredicate = cb.notEqual(root.get("typePersonnel"), 1);
            typePredicate = cb.and(typePredicate, cb.equal(root.get("supprimer"), false));
            typePredicate = cb.and(typePredicate, cb.equal(root.get("archiver"), false));
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

            query.orderBy(cb.asc(root.get("designation")));

            return cb.and(typePredicate, notExistsPredicate);
        };
    }

    public static Specification<Personnel> notInAbsenceAtDate(AbsenceDTO absenceDTO) {
        return (root, query, cb) -> {
            // Base predicates
            Predicate typePredicate = cb.notEqual(root.get("typePersonnel"), 1);
            typePredicate = cb.and(typePredicate, cb.equal(root.get("supprimer"), false));
            typePredicate = cb.and(typePredicate, cb.equal(root.get("archiver"), false));

            // Subquery 1: personnel who have a single full-day absence row (matin=true AND apresMidi=true)
            Subquery<Long> fullDaySubquery = query.subquery(Long.class);
            Root<Absence> abs1 = fullDaySubquery.from(Absence.class);
            List<Predicate> fullDayPreds = new ArrayList<>();
            fullDayPreds.add(cb.equal(abs1.get("personnel").get("id"), root.get("id")));
            fullDayPreds.add(cb.equal(abs1.get("dateAbsence"), absenceDTO.getDateAbsence()));
            fullDayPreds.add(cb.isTrue(abs1.get("matin")));
            fullDayPreds.add(cb.isTrue(abs1.get("apresMidi")));
            if (absenceDTO.getPersonnelId() != null) {
                fullDayPreds.add(cb.notEqual(abs1.get("personnel").get("id"), absenceDTO.getPersonnelId()));
            }
            fullDaySubquery.select(abs1.get("personnel").get("id")).where(fullDayPreds.toArray(new Predicate[0]));

            // Subquery 2: personnel who have a matin-only row (matin=true AND apresMidi=false)
            Subquery<Long> matinSubquery = query.subquery(Long.class);
            Root<Absence> abs2 = matinSubquery.from(Absence.class);
            List<Predicate> matinPreds = new ArrayList<>();
            matinPreds.add(cb.equal(abs2.get("personnel").get("id"), root.get("id")));
            matinPreds.add(cb.equal(abs2.get("dateAbsence"), absenceDTO.getDateAbsence()));
            matinPreds.add(cb.isTrue(abs2.get("matin")));
            matinPreds.add(cb.isFalse(abs2.get("apresMidi")));
            if (absenceDTO.getPersonnelId() != null) {
                matinPreds.add(cb.notEqual(abs2.get("personnel").get("id"), absenceDTO.getPersonnelId()));
            }
            matinSubquery.select(abs2.get("personnel").get("id")).where(matinPreds.toArray(new Predicate[0]));

            // Subquery 3: personnel who have an apresMidi-only row (matin=false AND apresMidi=true)
            Subquery<Long> apresMidiSubquery = query.subquery(Long.class);
            Root<Absence> abs3 = apresMidiSubquery.from(Absence.class);
            List<Predicate> apresMidiPreds = new ArrayList<>();
            apresMidiPreds.add(cb.equal(abs3.get("personnel").get("id"), root.get("id")));
            apresMidiPreds.add(cb.equal(abs3.get("dateAbsence"), absenceDTO.getDateAbsence()));
            apresMidiPreds.add(cb.isFalse(abs3.get("matin")));
            apresMidiPreds.add(cb.isTrue(abs3.get("apresMidi")));
            if (absenceDTO.getPersonnelId() != null) {
                apresMidiPreds.add(cb.notEqual(abs3.get("personnel").get("id"), absenceDTO.getPersonnelId()));
            }
            apresMidiSubquery.select(abs3.get("personnel").get("id")).where(apresMidiPreds.toArray(new Predicate[0]));

            // Equivalent to:
            // WHERE id NOT IN (
            //   SELECT personnel.id FROM Absence WHERE (date=:date AND matin=true AND apresMidi=true)
            //   OR ((date=:date AND matin=true AND apresMidi=false) AND (date=:date AND matin=false AND apresMidi=true))
            // )
            Predicate fullDayConflict    = cb.exists(fullDaySubquery);
            Predicate bothHalfDayConflict = cb.and(cb.exists(matinSubquery), cb.exists(apresMidiSubquery));

            Predicate notConflict = cb.not(cb.or(fullDayConflict, bothHalfDayConflict));

            query.orderBy(cb.asc(root.get("designation")));

            return cb.and(typePredicate, notConflict);
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
