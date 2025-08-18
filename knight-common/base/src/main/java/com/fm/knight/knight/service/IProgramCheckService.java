package com.fm.knight.knight.service;

import java.util.List;

public interface IProgramCheckService {

    /**
     * @param dimensionCode
     * @param resourceValue
     * @param operationValue
     * @return
     */
    List<String> findProgramItems(String dimensionCode, String resourceValue, String operationValue);

    List<String> findProgramItemsByUserId(String dimensionCode, String resourceValue, String operationValue, Long userId);
}
