package com.akranta.perfex_sb.dto;

import java.time.LocalDate;

public record DmtDashboardTransactionSummaryResponse(
        String flid,
        LocalDate fromDate,
        LocalDate toDate,

        Long suggestions,
        Long safetySuggestions,
        Long kaizens,
        Long safetyKaizens,
        Long opl,

        Long abnormalities,
        Long abnormalitiesClosed,
        Long abnormalitiesPending30,

        Long abnormalityPsi,
        Long abnormalityPsiClosed,
        Long abnormalityPsiGreater,

        Long meetings,
        Long meetingDiscussions,
        Long nearMiss,
        Long whyWhy,

        Long actionPlans,
        Long loss,
        Long kpi,
        Long susa,
        Long pjo,

        Long psi,
        Long psiObservations,
        Long psiClosed,
        Long psiPending,

        Long totalTransactions) {

    public static DmtDashboardTransactionSummaryResponse empty(
            String flid,
            LocalDate fromDate,
            LocalDate toDate) {

        return new DmtDashboardTransactionSummaryResponse(
                flid,
                fromDate,
                toDate,

                0L, // suggestions
                0L, // safetySuggestions
                0L, // kaizens
                0L, // safetyKaizens
                0L, // opl

                0L, // abnormalities
                0L, // abnormalitiesClosed
                0L, // abnormalitiesPending30

                0L, // abnormalityPsi
                0L, // abnormalityPsiClosed
                0L, // abnormalityPsiGreater

                0L, // meetings
                0L, // meetingDiscussions
                0L, // nearMiss
                0L, // whyWhy

                0L, // actionPlans
                0L, // loss
                0L, // kpi
                0L, // susa
                0L, // pjo

                0L, // psi
                0L, // psiObservations
                0L, // psiClosed
                0L, // psiPending

                0L // totalTransactions
        );
    }
}