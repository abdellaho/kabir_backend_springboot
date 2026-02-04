package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AchatFactureDTO;
import com.kabir.kabirbackend.entities.Absence;
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

    public Specification<AchatFacture> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateAF"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateAF"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateAF"), commonSearchModel.getDateDebut()));
                    }
                    if(null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateAF"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if(null != commonSearchModel.getFournisseurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fournisseur").get("id"), commonSearchModel.getPersonnelId()));
            }

            return predicate;
        };
    }

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
