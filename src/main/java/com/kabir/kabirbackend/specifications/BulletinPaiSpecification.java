package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.BulletinPaiDTO;
import com.kabir.kabirbackend.entities.BulletinPai;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class BulletinPaiSpecification implements Specification<BulletinPai> {

    public Specification<BulletinPai> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateDebut"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateDebut()));
                    }
                    if(null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateDebut"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if(null != commonSearchModel.getPersonnelId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("commercial").get("id"), commonSearchModel.getPersonnelId()));
            }
            if(null != commonSearchModel.getOperateurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("operateur").get("id"), commonSearchModel.getRepertoireId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateFin")));

            return predicate;
        };
    }

    public Specification<BulletinPai> searchIfExist(BulletinPaiDTO bulletinPaiDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(null != bulletinPaiDTO.getDateDebut() && null != bulletinPaiDTO.getDateFin()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateDebut"), bulletinPaiDTO.getDateDebut(), bulletinPaiDTO.getDateFin()));
            }
            if(null != bulletinPaiDTO.getCommercialId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("commercialId"), bulletinPaiDTO.getCommercialId()));
            }
            if(null != bulletinPaiDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), bulletinPaiDTO.getId()));
            }
            return predicate;
        };
    }

    private BulletinPaiDTO bulletinPaiDTO;

    @Override
    public Predicate toPredicate(Root<BulletinPai> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (bulletinPaiDTO != null) {
            if (null != bulletinPaiDTO.getCommercialId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("commercialId"), bulletinPaiDTO.getCommercialId()));
            }

            if(null != bulletinPaiDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), bulletinPaiDTO.getId()));
            }
        }
        return predicate;

    }
}