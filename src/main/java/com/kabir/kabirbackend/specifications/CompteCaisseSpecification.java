package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CompteCaisseSearch;
import com.kabir.kabirbackend.entities.CompteCaisse;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class CompteCaisseSpecification implements Specification<CompteCaisse> {

    private CompteCaisseSearch compteCaisseSearch;

    @Override
    public Predicate toPredicate(Root<CompteCaisse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if(compteCaisseSearch.isSearchByDate()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("date"), compteCaisseSearch.getDateDebutSearch(), compteCaisseSearch.getDateFinSearch()));
        }

        if (compteCaisseSearch != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("compteCaisse"), compteCaisseSearch.isCompteCaisse()));
        }

        return predicate;
    }
}