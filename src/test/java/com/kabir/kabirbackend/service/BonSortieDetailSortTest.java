package com.kabir.kabirbackend.service;

import com.kabir.kabirbackend.dto.BonSortieDetailPrintDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Regression test for the ordering required by the Jasper detail reports:
 * they group on {@code type} (and on {@code produit} for the all-products
 * report), so interleaved bon-sortie (type 0) / livraison (type 1) rows must
 * never reach the datasource. Plain JUnit: no Spring context and no database.
 */
class BonSortieDetailSortTest {

    private static Date date(int day) {
        return Date.from(LocalDate.of(2026, 11, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static BonSortieDetailPrintDTO row(int type, Date date, String num, String produit) {
        return new BonSortieDetailPrintDTO(type, date, num, 1, "", produit);
    }

    /** Compact "product/type/day" view of the datasource order. */
    private static List<String> signature(List<BonSortieDetailPrintDTO> rows) {
        List<String> result = new ArrayList<>();
        for (BonSortieDetailPrintDTO row : rows) {
            String day = null != row.getDate()
                    ? String.valueOf(row.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth())
                    : "null";
            result.add(row.getProduit() + "/" + row.getType() + "/" + day);
        }
        return result;
    }

    @Test
    void allProductsSortsProductThenTypeThenDate() {
        List<BonSortieDetailPrintDTO> rows = new ArrayList<>(List.of(
                row(1, date(9), "BL-A9", "Produit A"),
                row(0, date(8), "S-B8", "Produit B"),
                row(0, date(5), "S-A5", "Produit A"),
                row(1, date(2), "BL-B2", "Produit B"),
                row(1, date(1), "BL-A1", "Produit A"),
                row(0, date(7), "S-B7", "Produit B"),
                row(0, null, "S-A0", "Produit A")));

        BonSortieService.sortDetailRows(rows, true);

        assertEquals(List.of(
                "Produit A/0/null",
                "Produit A/0/5",
                "Produit A/1/1",
                "Produit A/1/9",
                "Produit B/0/7",
                "Produit B/0/8",
                "Produit B/1/2"), signature(rows));
    }

    @Test
    void singleProductSortsTypeThenDate() {
        List<BonSortieDetailPrintDTO> rows = new ArrayList<>(List.of(
                row(0, date(9), "S9", "Produit A"),
                row(1, date(3), "BL3", "Produit A"),
                row(0, date(4), "S4", "Produit A"),
                row(1, date(1), "BL1", "Produit A"),
                row(0, date(2), "S2", "Produit A"),
                row(1, date(5), "BL5", "Produit A")));

        BonSortieService.sortDetailRows(rows, false);

        assertEquals(List.of(
                "Produit A/0/2",
                "Produit A/0/4",
                "Produit A/0/9",
                "Produit A/1/1",
                "Produit A/1/3",
                "Produit A/1/5"), signature(rows));
    }
}
