package com.iidooo.core.action.index;

import org.apache.log4j.Logger;

import com.iidooo.core.action.common.BaseAction;

public class IndexAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(IndexAction.class);


    public String init() {
        try {

            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }
}
