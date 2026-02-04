package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AchatEtrangerDTO;
import com.kabir.kabirbackend.entities.AchatEtranger;
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
public class AchatEtrangerSpecification implements Specification<AchatEtranger> {

    public Specification<AchatEtranger> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateFacture"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateFacture"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateFacture"), commonSearchModel.getDateDebut()));
                    }
                    if(null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateFacture"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if(commonSearchModel.getFournisseurId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fournisseur").get("id"), commonSearchModel.getFournisseurId()));
            }

            if(null != commonSearchModel.getOperateurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("operateur").get("id"), commonSearchModel.getOperateurId()));
            }

            return predicate;
        };
    }

    private AchatEtrangerDTO achatEtrangerDTO;

    @Override
    public Predicate toPredicate(Root<AchatEtranger> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (achatEtrangerDTO != null) {
            if(StringUtils.isNotBlank(achatEtrangerDTO.getCodeFacture())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("codeFacture")), achatEtrangerDTO.getCodeFacture().trim().toLowerCase()));
            }

            if(null != achatEtrangerDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), achatEtrangerDTO.getId()));
            }
        }

        return predicate;
    }
}