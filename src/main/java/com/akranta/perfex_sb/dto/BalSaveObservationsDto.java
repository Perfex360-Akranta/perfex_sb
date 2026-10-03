package com.akranta.perfex_sb.dto;

import java.util.List;

import com.akranta.perfex_sb.model.BalPlmTlCalendar;
import com.akranta.perfex_sb.model.BalPlmTlObservations;

public class BalSaveObservationsDto 

{

    private BalPlmTlCalendar plmTlCalendarList;

     private List<BalPlmTlObservations> plmTlObservationList;

     public BalPlmTlCalendar getPlmTlCalendarList() {
         return plmTlCalendarList;
     }

     public void setPlmTlCalendarList(BalPlmTlCalendar plmTlCalendarList) {
         this.plmTlCalendarList = plmTlCalendarList;
     }

     public List<BalPlmTlObservations> getPlmTlObservationList() {
         return plmTlObservationList;
     }

     public void setPlmTlObservationList(List<BalPlmTlObservations> plmTlObservationList) {
         this.plmTlObservationList = plmTlObservationList;
     }


     



    
}
