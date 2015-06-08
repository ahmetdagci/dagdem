package com.tr.cay.dagdem.wrapper;

import com.tr.cay.dagdem.model.Tea;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EXT0175855 on 5/13/2015.
 */
public class TeaAdapterWrapper implements Serializable {

    private List<Tea> teaList;

    public TeaAdapterWrapper(List<Tea> teaList){
        this.teaList = teaList;
    }

    public List<Tea> getTeaList()
    {
        return teaList;
    }

}
