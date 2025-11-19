package org.example.clickearn.service;

import org.example.clickearn.entity.Campagne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampagneStats {
    private Campagne campagne;
    private Double totalBudgetAlloue;
    private Double totalMontantDepense;
    private Integer totalClics;
    private Integer totalConversions;

    public Double getTauxConversion() {
        if (totalClics == null || totalClics == 0) return 0.0;
        return (double) totalConversions / totalClics * 100;
    }

    public Double getCoutParClic() {
        if (totalClics == null || totalClics == 0) return 0.0;
        return totalMontantDepense != null ? totalMontantDepense / totalClics : 0.0;
    }

    public Double getBudgetRestant() {
        if (totalBudgetAlloue == null || totalMontantDepense == null) return 0.0;
        return totalBudgetAlloue - totalMontantDepense;
    }
}