package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.StockDTO;
import com.kabir.kabirbackend.entities.Stock;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Data
@Builder
public class StockSpecification implements Specification<Stock> {

    private StockDTO stockDTO;

    public static Specification<Stock> searchBySupprimerOrArchiver(StockDTO stockDTO) {
        return (root, query, criteriaBuilder) -> {

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("supprimer"), stockDTO.isSupprimer()),
                    criteriaBuilder.equal(root.get("archiver"), stockDTO.isArchiver())
            );
        };
    }

    public static Specification<Stock> getListStockBasedOnIds(List<Long> ids) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(CollectionUtils.isNotEmpty(ids)) {
                Expression<Long> id = root.get("id");
                predicate = criteriaBuilder.and(predicate, id.in(ids));
            } else {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("supprimer"), false),
                        criteriaBuilder.equal(root.get("archiver"), false)
                );
            }

            query.orderBy(criteriaBuilder.asc(root.get("designation")));

            return predicate;
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
