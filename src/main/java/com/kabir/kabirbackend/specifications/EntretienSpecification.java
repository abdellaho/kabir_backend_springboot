package com.kabir.kabirbackend.specifications;


import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.EntretienDTO;
import com.kabir.kabirbackend.entities.Entretien;
import jakarta.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class EntretienSpecification {

    public static Specification<Entretien> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateEntretien"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateEntretien"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateEntretien"), commonSearchModel.getDateDebut()));
                    }
                    if(null != commonSearchModel.getDateFin()) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateEntretien"), commonSearchModel.getDateFin()));
                    }
                }
            }
            if(null != commonSearchModel.getVoitureId() && commonSearchModel.getVoitureId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("voiture").get("id"), commonSearchModel.getVoitureId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateEntretien")));

            return predicate;
        };
    }

    public static Specification<Entretien> exist(EntretienDTO entretienDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(null != entretienDTO.getDateEntretien()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateEntretien"), entretienDTO.getDateEntretien()));
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("kmDetecte"), entretienDTO.getKmDetecte()));
            }
            if(null != entretienDTO.getVoitureId() && entretienDTO.getVoitureId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("voiture").get("id"), entretienDTO.getVoitureId()));
            }
            if(null != entretienDTO.getId() && entretienDTO.getId() > 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), entretienDTO.getId()));
            }

            return predicate;
        };
    }
}
