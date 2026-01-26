package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Repertoire;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class RepertoireSpecification implements Specification<Repertoire> {

    private RepertoireDTO repertoireDTO;

    public static Specification<Repertoire> searchBySupprimerOrArchiver(RepertoireDTO repertoireDTO) {
        return (root, query, criteriaBuilder) -> {

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("bloquer"), repertoireDTO.isBloquer()),
                    criteriaBuilder.equal(root.get("archiver"), repertoireDTO.isArchiver())
            );
        };
    }

    public static Specification<Repertoire> searchBySupprimerOrArchiverAndClientsOnly(RepertoireDTO repertoireDTO) {
        return (root, query, criteriaBuilder) -> {

            List<Integer> intCriteria = Arrays.asList(0, 1, 2);

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("bloquer"), repertoireDTO.isBloquer()),
                    criteriaBuilder.equal(root.get("archiver"), repertoireDTO.isArchiver()),
                    root.get("typeRepertoire").in(intCriteria)
            );
        };
    }

    @Override
    public Predicate toPredicate(Root<Repertoire> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();
        if (repertoireDTO != null) {
            if (StringUtils.isNotBlank(repertoireDTO.getDesignation())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(criteriaBuilder.lower(root.get("designation")), repertoireDTO.getDesignation().toLowerCase().trim()));
            }

            if (StringUtils.isNotBlank(repertoireDTO.getTel1())) {
                Predicate tel1Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), repertoireDTO.getTel1()),
                        criteriaBuilder.equal(root.get("tel2"), repertoireDTO.getTel1())
                );

                predicate = criteriaBuilder.or(predicate, tel1Predicate);
            }

            if (StringUtils.isNotBlank(repertoireDTO.getTel2())) {
                Predicate tel2Predicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("tel1"), repertoireDTO.getTel2()),
                        criteriaBuilder.equal(root.get("tel2"), repertoireDTO.getTel2())
                );

                predicate = criteriaBuilder.or(predicate, tel2Predicate);
            }

            if (null != repertoireDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), repertoireDTO.getId()));
            }
        }

        return predicate;
    }
}
