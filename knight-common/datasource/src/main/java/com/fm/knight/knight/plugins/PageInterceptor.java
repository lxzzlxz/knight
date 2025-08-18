package com.fm.knight.knight.plugins;

import com.fm.knight.knight.override.KnightBoundSql;
import com.fm.knight.knight.page.Page;
import com.fm.knight.knight.page.PagedResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis分页拦截器
 *
 * @author weihao
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {

    private static final Log log = LogFactory.getLog(PageInterceptor.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Executor executor = (Executor) invocation.getTarget();

        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameter = args[1];
        String sqlId = mappedStatement.getId();
        if (sqlId.endsWith("Count")) {

            return invocation.proceed();
        }

        Page page = null;
        if (parameter instanceof Map) {
            Map paraMap = (Map) parameter;
            for (Object obj : paraMap.values()) {
                if (obj instanceof Page) {
                    page = (Page) obj;
                    break;
                }
            }
        }

        if (parameter instanceof Page) {
            page = (Page) parameter;
        }

        if (null == page) {
            return invocation.proceed();
        }

        Configuration configuration = mappedStatement.getConfiguration();
        String countSqlId = sqlId + "Count";
        MappedStatement countMappedStatement = configuration.getMappedStatement(countSqlId);

        final RowBounds rowBounds = (RowBounds) args[2];
        final ResultHandler resultHandler = (ResultHandler) args[3];
        BoundSql boundSqlCount = countMappedStatement.getBoundSql(parameter);
        boundSqlCount = new KnightBoundSql(configuration, boundSqlCount);
        StatementHandler statementHandlerCount = configuration.newStatementHandler(executor, countMappedStatement, parameter, rowBounds, resultHandler, boundSqlCount);
        Connection conn = ConnectionLogger.newInstance(executor.getTransaction().getConnection(), log, 1);

        Statement stat = null;
        List resultList = null;
        try {
            stat = statementHandlerCount.prepare(conn, 120);
            statementHandlerCount.parameterize(stat);
            resultList = statementHandlerCount.query(stat, resultHandler);
        } catch (Exception e) {
            log.error("page interceptor error|", e);
        } finally {
            if (null != stat) {
                stat.close();
            }
        }
        Long totalCount = 0L;
        if (!CollectionUtils.isEmpty(resultList)) {
            totalCount = (Long) resultList.get(0);
        }
        page.setTotalCount(totalCount);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);

        if (null == totalCount || 0 == totalCount || totalCount <= page.getStartIndex()) {
            pagedResult.setResultList(new ArrayList<>());
            List result = new ArrayList<>();
            result.add(pagedResult);
            return result;
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        StatementHandler statementHandler = configuration.newStatementHandler(executor, mappedStatement, parameter, rowBounds, resultHandler, boundSql);

        Statement stat1;
        try {
            stat1 = statementHandler.prepare(conn, 120);
            statementHandler.parameterize(stat1);
            resultList = statementHandler.query(stat1, resultHandler);
            pagedResult.setResultList(resultList);
        } catch (Exception e) {
            log.error("page interceptor error|", e);
        }
        List result = new ArrayList<>();
        result.add(pagedResult);
        return result;
    }

    @Override
    public Object plugin(Object arg0) {

        return Plugin.wrap(arg0, this);
    }

    @Override
    public void setProperties(Properties arg0) {

    }

}
