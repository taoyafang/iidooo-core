package com.iidooo.core.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.iidooo.core.constant.ClassConstant;
import com.iidooo.core.dao.extend.DictItemDao;
import com.iidooo.core.dto.extend.DictItemDto;
import com.iidooo.core.util.MybatisUtil;
import com.iidooo.core.util.SpringUtil;
import com.iidooo.core.util.StringUtil;

public class DictItemSelectTag extends SimpleTagSupport{
    private static final Logger logger = Logger.getLogger(DictItemSelectTag.class);

    private final String HTML_SELECT = "<select id='{0}' name='{1}'>";
    
    private final String HTML_SELECT_DISABLED = "<select id='{0}' name='{1}' disabled='disabled'>";

    private final String HTML_OPTION = "<option value='{0}'>{1}</option>";

    private final String HTML_OPTION_SELECT = "<option value='{0}' selected='selected'>{1}</option>";

    private String id;

    private String dictClassCode;

    private boolean isContainBlank = false;
    
    private boolean isDiabled = false;

    private String name;

    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictClassCode() {
        return dictClassCode;
    }

    public void setDictClassCode(String dictClassCode) {
        this.dictClassCode = dictClassCode;
    }

    public boolean getIsContainBlank() {
        return isContainBlank;
    }

    public void setIsContainBlank(boolean isContainBlank) {
        this.isContainBlank = isContainBlank;
    }

    public boolean getIsDiabled() {
        return isDiabled;
    }

    public void setIsDiabled(boolean isDabled) {
        this.isDiabled = isDabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        PageContext pageContext = null;
        JspWriter out = null;
        try {
            pageContext = (PageContext) getJspContext();
            out = pageContext.getOut();

            if (isDiabled) {
                out.println(StringUtil.replace(HTML_SELECT_DISABLED, id, name));    
            } else {
                out.println(StringUtil.replace(HTML_SELECT, id, name));                
            }
            
            if (isContainBlank) {
                out.println(StringUtil.replace(HTML_OPTION, "0", ""));
            }
            
            DictItemDao dictItemDao = sqlSession.getMapper(DictItemDao.class);
            List<DictItemDto> dictItemList = dictItemDao.selectByClassCode(dictClassCode);
            
            for (DictItemDto item : dictItemList) {
                String dictItemCode = item.getDictItemCode();
                String dictItemName = item.getDictItemName();
                if (value != null && !value.isEmpty() && value.equals(dictItemCode)) {
                    out.println(StringUtil.replace(HTML_OPTION_SELECT, dictItemCode, dictItemName));
                } else {
                    out.println(StringUtil.replace(HTML_OPTION, dictItemCode, dictItemName));
                }
            }
            out.println("</select>");
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}
