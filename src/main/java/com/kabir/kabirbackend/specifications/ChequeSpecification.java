package com.kabir.kabirbackend.specifications;


import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.entities.Cheque;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class ChequeSpecification implements Specification<Cheque> {

    private ChequeDTO chequeDTO;

    @Override
    public Predicate toPredicate(Root<Cheque> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (chequeDTO != null) {
            if (chequeDTO.getDateCheque() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateCheque"), chequeDTO.getDateCheque()));
            }

            if(chequeDTO.getCodeCheque() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codeCheque"), chequeDTO.getCodeCheque()));
            }

            if(null != chequeDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), chequeDTO.getId()));
            }
        }

        return predicate;
    }
}
