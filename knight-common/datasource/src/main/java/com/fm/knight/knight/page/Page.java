package com.fm.knight.knight.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page implements Serializable {

    private static final long serialVersionUID = 4121168324340189627L;

    private Integer curPage;

    private Integer pageSize;

    private Integer startIndex;

    private Long totalCount = 0L;

}
