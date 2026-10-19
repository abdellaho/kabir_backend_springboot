package com.kabir.kabirbackend.config.util;

/**
 * Port of the legacy {@code ma.kabir.conf.ConvertierMontantEnLettre}.
 * <p>
 * Used by the facture row print ({@code BonFactr.jrxml}) to fill the
 * {@code MntTotalLettre} parameter (amount in words). The algorithm is kept
 * faithful to the legacy implementation; the only deliberate hardening is that
 * {@link #setMontant(String)} tolerates a comma decimal separator, a missing
 * decimal part and a blank/null integer part (legacy would throw on those).
 */
public class MontantEnLettreUtil {

    private String[] group_unit = {"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix sept", "dix huit", "dix neuf"};
    private String[] group_diz = {"", "dix", "vingt", "trente", "quarante", "cinquante", "soixante", "", "quatre vingt"};
    private String[] group_cent = {"", "cent", "mille", "million", "milliard"};
    private String[] group_mi = {"", "mille", "million", "milliard"};
    private String montantLettre = "";
    private String montant = "0.00";
    private long partieEnt = 0;
    private int partieFraq = 0;
    private String unite;
    private String sousUnite;

    public MontantEnLettreUtil() {
        unite = "Dirhams";
        sousUnite = "centimes";
    }

    public MontantEnLettreUtil(String unite, String sousUnite) {
        this.unite = unite;
        this.sousUnite = sousUnite;
    }

    public String getMontant() {
        return montant;
    }

    public String getMontantLettre() {
        return montantLettre;
    }

    public String getSousUnite() {
        return sousUnite;
    }

    public void setSousUnite(String sousUnite) {
        this.sousUnite = sousUnite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public void setMontant(String montantChiffre) {
        montant = null == montantChiffre ? "0" : montantChiffre.replace(",", ".").trim();

        String[] split = montant.split("\\.");
        this.partieEnt = (split.length > 0 && !split[0].isBlank()) ? Long.parseLong(split[0].trim()) : 0L;
        this.partieFraq = (split.length > 1 && !split[1].isBlank()) ? Integer.parseInt(split[1].trim()) : 0;
    }

    private String calculer(int val) {
        int r = val % 100;
        int d = val / 100;
        String s = "";
        if (r < 20) {
            s = group_unit[r];
        } else {
            int r1 = r % 10;
            int r2 = r / 10;
            if (r2 < 7 || r2 == 8) {
                s = group_diz[r2];
                if (r2 == 8 && r1 == 0) {
                    s += "s";
                }
                s += " " + group_unit[r1];
            } else {
                s = group_diz[r2 - 1] + " " + group_unit[r1 + 10];
            }
        }
        if (d != 0) {
            s = group_cent[1] + " " + s;
            if (d > 1) {
                s = group_unit[d] + " " + s;
            }
        }
        return s;
    }

    public void calculer_glob() {
        int i = 0;
        int r = 0;
        long nb = partieEnt;
        String s = "";
        montantLettre = "";
        do {
            r = (int) (nb % 1000);
            nb = nb / 1000;
            if (r != 0) {
                if (r == 1 && i == 1) {
                    s = group_mi[i];
                } else {
                    s = calculer(r) + " " + group_mi[i];
                }
            } else {
                s = "";
            }
            montantLettre = s + " " + montantLettre;
            i++;
        } while (nb != 0);
        if (partieFraq != 0) {
            montantLettre = montantLettre + " " + unite + " et " + calculer(partieFraq) + " " + sousUnite;
        } else {
            montantLettre = montantLettre + " " + unite;
        }
    }

    /**
     * Convenience used by the print services: converts a numeric amount to words
     * using the legacy {@code DecimalFormat("#.00")} / comma-to-dot handling.
     */
    public static String convertir(double montant) {
        java.text.DecimalFormat format = new java.text.DecimalFormat("#.00");
        MontantEnLettreUtil convertisseur = new MontantEnLettreUtil();
        convertisseur.setMontant(format.format(montant).replace(",", "."));
        convertisseur.calculer_glob();
        return convertisseur.getMontantLettre();
    }
}
