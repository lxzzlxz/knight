package com.fm.knight.knight.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements Serializable {
    private static final long serialVersionUID = -3476288055541094087L;
    private Long id;
    private String rId;
    private String rName;
    private String prId;
    private String prName;
    private String scope;
    private Date creationDate;
    public void setrId(String rId) {
        this.rId = rId;
    }
    public void setrName(String rName) {
        this.rName = rName;
    }
}
