package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.PrimeDTO;
import com.kabir.kabirbackend.entities.Prime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Builder
@Data
public class PrimeSpecification {

    public static Specification<Prime> searchBySupprimerOrArchiver(PrimeDTO primeDTO) {
        return (root, query, criteriaBuilder) -> {

            return criteriaBuilder.and(
                    criteriaBuilder.greaterThanOrEqualTo(root.get("montant"), primeDTO.getMontant())
            );
        };
    }
}
