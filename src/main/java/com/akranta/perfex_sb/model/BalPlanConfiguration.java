package com.akranta.perfex_sb.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="bal_gen_tl_annualtable", schema="public")

public class BalPlanConfiguration {

    @Id
    @Column(name="antb_monthname")
    private LocalDateTime monthname;

    @Column(name="antb_weekname")
    private Integer weekname;

    public LocalDateTime getMonthname() {
        return monthname;
    }

    public void setMonthname(LocalDateTime monthname) {
        this.monthname = monthname;
    }

    public Integer getWeekname() {
        return weekname;
    }

    public void setWeekname(Integer weekname) {
        this.weekname = weekname;
    }

}
