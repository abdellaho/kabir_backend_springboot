package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.LivraisonDTO;
import com.kabir.kabirbackend.entities.Livraison;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


@Data
@Builder
public class LivraisonSpecification implements Specification<Livraison> {

    public static Specification<Livraison> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (commonSearchModel.isSearchByDate()) {
                if (null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if (commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateBl"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateBl"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if (null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if (null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateBl"), commonSearchModel.getDateDebut()));
                        }
                        if (null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateBl"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateBl"), dateDebut, dateFin));
                    }
                }
            }

            if (StringUtils.isNotBlank(commonSearchModel.getNumCheque())) {
                Predicate predicateNumCheque = criteriaBuilder.like(criteriaBuilder.lower(root.get("numCheque")), "%" + commonSearchModel.getNumCheque().trim().toLowerCase() + "%");
                Predicate predicateNumCheque2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("numCheque2")), "%" + commonSearchModel.getNumCheque().trim().toLowerCase() + "%");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(predicateNumCheque, predicateNumCheque2));
            }


            if (null != commonSearchModel.getRepertoireId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("repertoire").get("id"), commonSearchModel.getRepertoireId()));
            }
            if (null != commonSearchModel.getOperateurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("employeOperateur").get("id"), commonSearchModel.getOperateurId()));
            }
            if (null != commonSearchModel.getPersonnelId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), commonSearchModel.getPersonnelId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateBl")), criteriaBuilder.desc(root.get("codeBl")));

            return predicate;
        };
    }

    private LivraisonDTO livraisonDTO;

    @Override
    public Predicate toPredicate(Root<Livraison> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
        if (livraisonDTO != null) {
            if (StringUtils.isNotBlank(livraisonDTO.getCodeBl())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("codeBl")), livraisonDTO.getCodeBl().toLowerCase().trim()));
            }

            if (null != livraisonDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), livraisonDTO.getId()));
            }
        }

        return predicate;
    }
}
