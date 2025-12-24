package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.FournisseurDTO;
import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Fournisseur;
import com.kabir.kabirbackend.entities.Stock;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class StockSpecification implements Specification<Stock> {

    private StockDTO stockDTO;

    public static Specification<Stock> searchBySupprimerOrArchiver(StockDTO stockDTO) {
        return (root, query, criteriaBuilder) -> {

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("supprimer"), stockDTO.isSupprimer()),
                    criteriaBuilder.equal(root.get("archiver"), stockDTO.isArchiver())
            );
        };
    }


    @Override
    public Predicate toPredicate(Root<Stock> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (stockDTO != null) {
            if (stockDTO.getDesignation() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), stockDTO.getDesignation().toLowerCase().trim()));
            }

            if(null != stockDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), stockDTO.getId()));
            }
        }
        return predicate;

    }
}
