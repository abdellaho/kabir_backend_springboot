package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Absence;
import com.kabir.kabirbackend.entities.Livraison;
import com.kabir.kabirbackend.entities.Repertoire;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class RepertoireSpecification implements Specification<Repertoire> {

    private RepertoireDTO repertoireDTO;

    public static Specification<Repertoire> searchToPrint(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("typeRepertoire"), commonSearchModel.getTypeRepertoire()));

            if(null != commonSearchModel.getVilleId() && commonSearchModel.getVilleId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ville").get("id"), commonSearchModel.getVilleId()));
            }
            if(null != commonSearchModel.getPersonnelId() && commonSearchModel.getPersonnelId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), commonSearchModel.getPersonnelId()));
            }

            if(commonSearchModel.getTypeImprimRepertoire() == 0) {
                if(null == commonSearchModel.getVilleId() || commonSearchModel.getVilleId() == 0) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("nbrOperationClient"), 0));
                }
            } else if(commonSearchModel.getTypeImprimRepertoire() == 1) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("nbrOperationClient"), 0));
            } else if(commonSearchModel.getTypeImprimRepertoire() == 2) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("nbrOperationClient"), 1));
            } else if(commonSearchModel.getTypeImprimRepertoire() == 3) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("nbrOperationClient"), 1));
            } else if(commonSearchModel.getTypeImprimRepertoire() == 8) {
                Predicate tel1Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel1")), criteriaBuilder.notEqual(root.get("tel1"), ""));
                Predicate tel2Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel2")), criteriaBuilder.notEqual(root.get("tel2"), ""));
                Predicate tel3Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel3")), criteriaBuilder.notEqual(root.get("tel3"), ""));
                Predicate tel1IsEmpty = criteriaBuilder.or(criteriaBuilder.isNull(root.get("tel1")), criteriaBuilder.equal(root.get("tel1"), ""));
                Predicate tel2IsEmpty = criteriaBuilder.or(criteriaBuilder.isNull(root.get("tel2")), criteriaBuilder.equal(root.get("tel2"), ""));
                Predicate tel3IsEmpty = criteriaBuilder.or(criteriaBuilder.isNull(root.get("tel3")), criteriaBuilder.equal(root.get("tel3"), ""));

                Predicate tel1Only = criteriaBuilder.and(tel1Filled, tel2IsEmpty, tel3IsEmpty);
                Predicate tel2Only = criteriaBuilder.and(tel2Filled, tel1IsEmpty, tel3IsEmpty);
                Predicate tel3Only = criteriaBuilder.and(tel3Filled, tel1IsEmpty, tel2IsEmpty);

                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(tel1Only, tel2Only, tel3Only));
            } else if(commonSearchModel.getTypeImprimRepertoire() == 9) {
                Predicate tel1Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel1")), criteriaBuilder.notEqual(root.get("tel1"), ""));
                Predicate tel2Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel2")), criteriaBuilder.notEqual(root.get("tel2"), ""));
                Predicate tel3Filled = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("tel3")), criteriaBuilder.notEqual(root.get("tel3"), ""));

                Predicate tel1Tel2 = criteriaBuilder.and(tel1Filled, tel2Filled);
                Predicate tel1Tel3 = criteriaBuilder.and(tel1Filled, tel3Filled);
                Predicate tel2Tel3 = criteriaBuilder.and(tel2Filled, tel3Filled);

                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(tel1Tel2, tel1Tel3, tel2Tel3));
            } else {
                Predicate nbrOpPredicate = criteriaBuilder.greaterThan(root.get("nbrOperationClient"), 0);

                // compute localDateFin
                LocalDate localDateFin = LocalDate.now();

                if (commonSearchModel.getTypeImprimRepertoire() == 4 || commonSearchModel.getTypeImprimRepertoire() == 7) {
                    localDateFin = localDateFin.minusYears(1);
                }

                if (commonSearchModel.getTypeImprimRepertoire() == 5 || commonSearchModel.getTypeImprimRepertoire() == 6) {
                    localDateFin = localDateFin.minusMonths(6);
                }

                // --- Subquery ---
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Livraison> livraisonRoot = subquery.from(Livraison.class);

                subquery.select(livraisonRoot.get("repertoire").get("id"));
                subquery.where(criteriaBuilder.greaterThan(livraisonRoot.get("dateBl"), localDateFin));

                // IN or NOT IN
                Predicate inPredicate = root.get("id").in(subquery);

                Predicate finalPredicate;
                if (commonSearchModel.getTypeImprimRepertoire() == 6 || commonSearchModel.getTypeImprimRepertoire() == 4) {
                    finalPredicate = criteriaBuilder.and(nbrOpPredicate, inPredicate);
                } else {
                    finalPredicate = criteriaBuilder.and(nbrOpPredicate, criteriaBuilder.not(inPredicate));
                }

                predicate = criteriaBuilder.and(predicate, finalPredicate);
            }

            return predicate;
        };
    }

    public static Specification<Repertoire> searchBySupprimerOrArchiver(RepertoireDTO repertoireDTO) {
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("bloquer"), repertoireDTO.isBloquer()),
                    criteriaBuilder.equal(root.get("archiver"), repertoireDTO.isArchiver())
            );
        };
    }

    public static Specification<Repertoire> searchBySupprimerOrArchiverAndClientsOnly(RepertoireDTO repertoireDTO) {
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            List<Integer> intCriteria = Arrays.asList(0, 1, 2);

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("bloquer"), repertoireDTO.isBloquer()),
                    criteriaBuilder.equal(root.get("archiver"), repertoireDTO.isArchiver()),
                    root.get("typeRepertoire").in(intCriteria)
            );
        };
    }

    @Override
    public Predicate toPredicate(Root<Repertoire> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (repertoireDTO != null) {
            if (StringUtils.isNotBlank(repertoireDTO.getDesignation())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), repertoireDTO.getDesignation().toLowerCase().trim()));
            }

            if (StringUtils.isNotBlank(repertoireDTO.getTel1())) {
                Predicate tel1Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), repertoireDTO.getTel1()),
                        criteriaBuilder.equal(root.get("tel2"), repertoireDTO.getTel1())
                );

                predicate = criteriaBuilder.or(predicate, tel1Predicate);
            }

            if (StringUtils.isNotBlank(repertoireDTO.getTel2())) {
                Predicate tel2Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), repertoireDTO.getTel2()),
                        criteriaBuilder.equal(root.get("tel2"), repertoireDTO.getTel2())
                );

                predicate = criteriaBuilder.or(predicate, tel2Predicate);
            }

            if (StringUtils.isNotBlank(repertoireDTO.getIce())) {
                predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("ice")), repertoireDTO.getIce().trim().toLowerCase()));
            }

            if (null != repertoireDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), repertoireDTO.getId()));
            }
        }

        query.orderBy(criteriaBuilder.asc(root.get("designation")));

        return predicate;
    }
}
