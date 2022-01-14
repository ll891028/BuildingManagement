package com.liulin.system.domain.dto;

import com.liulin.system.domain.Resident;

public class ResidentDto extends Resident {

    private String residentIds;

    private Boolean checkAll;

    public String getResidentIds() {
        return residentIds;
    }

    public void setResidentIds(String residentIds) {
        this.residentIds = residentIds;
    }

    public Boolean getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(Boolean checkAll) {
        this.checkAll = checkAll;
    }
}
