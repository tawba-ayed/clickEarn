package org.example.clickearn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardStatsDTO {
    private Double totalBudgetDepense;
    private Integer totalClics;
    private Integer publicationsActives;
    private Double tauxClicsMoyen;
    private Double retourSurInvestissement;

    public DashboardStatsDTO() {
        this.totalBudgetDepense = 2500.0;
        this.totalClics = 12890;
        this.publicationsActives = 834;
        this.tauxClicsMoyen = 2.5;
        this.retourSurInvestissement = 12.0;
    }
}