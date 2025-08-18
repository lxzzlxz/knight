package com.fm.knight.knight.override;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnightSqlSource implements SqlSource {
    private SqlSource sqlSource;
    private Configuration configuration;

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new KnightBoundSql(configuration, sqlSource.getBoundSql(parameterObject));
    }
}
