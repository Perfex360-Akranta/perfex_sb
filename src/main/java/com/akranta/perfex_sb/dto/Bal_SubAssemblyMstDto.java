package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_GenTlSubAssemblymst;
import com.akranta.perfex_sb.model.BAL_GenTlFunctionallocn;

public class Bal_SubAssemblyMstDto 
{
    private BAL_GenTlSubAssemblymst subAssemblymst;
    private BAL_GenTlFunctionallocn genTlFunctionallocn;
    private String delemode; // for future delete reuse, same convention as assembly

    public BAL_GenTlSubAssemblymst getSubAssemblymst() { 
        return subAssemblymst; 
    }
    public void setSubAssemblymst(BAL_GenTlSubAssemblymst subAssemblymst) {
         this.subAssemblymst = subAssemblymst; 
        }
    public BAL_GenTlFunctionallocn getGenTlFunctionallocn() {
         return genTlFunctionallocn; 
        }
    public void setGenTlFunctionallocn(BAL_GenTlFunctionallocn genTlFunctionallocn) {
         this.genTlFunctionallocn = genTlFunctionallocn; 
        }
    public String getDelemode() {
         return delemode; 
        }
    public void setDelemode(String delemode) {
         this.delemode = delemode; 
        }
}
