package com.fm.knight.knight.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResult<T> implements Serializable {
    private static final long serialVersionUID = 176319318287181287L;
    private Page page;
    private List<T> resultList = new ArrayList<>();

    public PagedResult(Page page) {
        super();
        this.page = page;
    }
}
