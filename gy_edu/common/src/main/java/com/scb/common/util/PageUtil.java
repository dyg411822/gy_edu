package com.scb.common.util;

import com.scb.common.page.PageVO;

/**
 * @author
 * @date
 */
@Deprecated
public class PageUtil {

    public static PageVO changePage(PageVO pageVo) {
        if (null == pageVo) {
            pageVo = new PageVO();
        }
        int start = getStart(pageVo.getPageNum(), pageVo.getPageSize());
        int end = getEnd(pageVo.getPageSize());
        pageVo.setStart(start);
        pageVo.setEnd(end);
        return pageVo;
    }

    public static int getStart(int pageNum, int pageSize) {
        if (pageNum <= 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        int start = (pageNum - 1) * pageSize;
        return start;
    }

    public static int getEnd(int pageSize) {
        if (pageSize < 1) {
            pageSize = 10;
        }
        return pageSize;
    }

    public static <T extends PageVO> T changeReq(T req) {
        req.setStart(PageUtil.getStart(req.getPageNum(), req.pageSize));
        req.setEnd(PageUtil.getEnd(req.getPageSize()));
        return req;
    }
}
