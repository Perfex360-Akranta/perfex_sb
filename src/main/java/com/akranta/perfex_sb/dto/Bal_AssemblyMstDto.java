package com.akranta.perfex_sb.dto;

import com.akranta.perfex_sb.model.BAL_GenTlAssemblymst;
import com.akranta.perfex_sb.model.BAL_GenTlFunctionallocn;

public class Bal_AssemblyMstDto 
{
    private BAL_GenTlAssemblymst assemblymst;
    private BAL_GenTlFunctionallocn genTlFunctionallocn;
    private String delemode;
    public BAL_GenTlAssemblymst getAssemblymst() {
        return assemblymst;
    }
    public void setAssemblymst(BAL_GenTlAssemblymst assemblymst) {
        this.assemblymst = assemblymst;
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
