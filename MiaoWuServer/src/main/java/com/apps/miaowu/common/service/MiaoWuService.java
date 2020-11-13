package com.apps.miaowu.common.service;

import com.apps.miaowu.common.exception.MiaowuException;
import com.apps.miaowu.constant.NormalConstant;

/**
 * @author 梁键兴
 */
public interface MiaoWuService {

    default void varifyNullParam(Object... objects) throws MiaowuException {
        boolean flag = true;
        for (Object obj :
                objects) {
            if (obj == null) {
                throw new MiaowuException(NormalConstant.ILLEGAL_PARAM);
            }
        }
    }
}
