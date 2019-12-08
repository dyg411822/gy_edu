package com.scb.common.BaseEntity;

import lombok.Data;

/**
 * excel导出model
 * @author Administrator
 *
 */
@Data
public class BaseExcelModel {//"orderNum","productName","productPrice","createTime"
	private String orderNum;//页标题
	private String productName;//页脚
	private String productPrice;//页标题
	private String createTime;//页脚
}
