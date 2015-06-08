package com.tr.cay.dagdem.model;

import java.io.Serializable;

/**
 * Created by EXT0175855 on 5/15/2015.
 */
public class OraletTea implements Serializable {

    private String  isim;
    private boolean checked = false;
    private int adet;

    public OraletTea(String isim) {
        super();
        this.isim = isim;
    }

    @Override
    public String toString() {
        return isim;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public int getAdet()
    {
        return adet;
    }

    public void setAdet(int adet)
    {
        this.adet = adet;
    }
}
