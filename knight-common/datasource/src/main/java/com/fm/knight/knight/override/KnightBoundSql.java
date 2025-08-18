package com.fm.knight.knight.override;

import com.fm.knight.knight.context.AppContext;
import com.fm.knight.knight.service.IProgramCheckService;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KnightBoundSql extends BoundSql {

    private Configuration configuration;

    private final BoundSql boundSql;

    private IProgramCheckService programCheckService;

    private static final String patternName = "<program.*</program>";

    public KnightBoundSql(Configuration configuration, BoundSql bSql) {
        super(configuration, bSql.getSql(), bSql.getParameterMappings(), bSql.getParameterObject());
        this.boundSql = bSql;
        this.setConfiguration(configuration);
    }

    @Override
    public String getSql() {
        String origin = boundSql.getSql();
        if (!origin.contains("<program")) {
            return origin;
        }


        if (null == programCheckService) {
            Object obj = AppContext.getContext().getBean("programCheckService");
            programCheckService = (IProgramCheckService) obj;
        }

        Pattern pattern = Pattern.compile(patternName);
        Matcher matcher = pattern.matcher(origin);

        SAXReader reader = new SAXReader();
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.delete(0, sb.length());
            String program = matcher.group();
            String condition = "";
            boolean isAll = false;
            try {
                Document document = reader.read(new ByteArrayInputStream(program.getBytes(StandardCharsets.UTF_8)));
                Element element = document.getRootElement();
                String dimension2Field = element.attributeValue("dimension2Field");
                String prepend = element.attributeValue("prepend");
                String resourceId = element.attributeValue("resourceValue");
                String operationId = element.attributeValue("operationValue");
                String[] ary = dimension2Field.split(":");
                sb.append(" ").append(prepend).append(" ");

                Set<String> set = new HashSet<>();
                if (null != programCheckService) {
                    List<String> lst = programCheckService.findProgramItems(ary[0], resourceId, operationId);
                    if (!CollectionUtils.isEmpty(lst)) {
                        sb.append(ary[1]).append(" in ( ");
                        for (String s : lst) {
                            if ("@All".equals(s)) {
                                isAll = true;
                                break;
                            }
                            if (set.contains(s)) {
                                continue;
                            }
                            set.add(s);
                            sb.append("'").append(s).append("',");
                        }
                        if (sb.length() > 0) {
                            sb.delete(sb.length() - 1, sb.length());
                        }
                        sb.append(" ) ");
                    } else {
                        sb.append(" 1 = 2 ");
                    }
                }
                if (isAll) {
                    condition = " " + prepend + " 1 = 1 ";
                } else {
                    condition = sb.toString();
                }
            } catch (DocumentException e) {

                e.printStackTrace();
            } finally {
                origin = origin.replaceFirst(program, condition);
            }
        }
        return origin;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
