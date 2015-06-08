package com.tr.cay.dagdem.wrapper;

import com.tr.cay.dagdem.model.OraletTea;
import com.tr.cay.dagdem.model.Tea;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EXT0175855 on 5/15/2015.
 */
public class OraletTeaAdapterWrapper implements Serializable {

    private List<OraletTea> oraletTeaList;

    public OraletTeaAdapterWrapper(List<OraletTea> oraletTeaList)
    {
        this.oraletTeaList = oraletTeaList;
    }

    public List<OraletTea> getTeaList()
    {
        return oraletTeaList;
    }
}
