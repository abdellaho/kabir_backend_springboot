package com.kabir.kabirbackend.specifications;


import com.kabir.kabirbackend.dto.VoitureDTO;
import com.kabir.kabirbackend.entities.Voiture;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
@Builder
public class VoitureSpecification implements Specification<Voiture> {

    private VoitureDTO voitureDTO;

    @Override
    public Predicate toPredicate(Root<Voiture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (voitureDTO != null) {
            if (voitureDTO.getNumVoiture() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("numVoiture"), voitureDTO.getNumVoiture()));
            }

            if(null != voitureDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), voitureDTO.getId()));
            }
        }

        return predicate;
    }
}
