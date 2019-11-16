package com.leyou.common.myexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author WJ
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum LyException {

    /**
     * 分类信息无法找到
     */
    CATEGORY_NOT_FOUND(404,"商品分类没查到"),

    /**
     * 新增品牌失败
     */
    BRAND_SAVE_ERROR(500,"新增品牌失败"),

    /**
     * 品牌信息无法找到
     */
    BRAND_NOT_FOUND(404,"品牌不存在"),
    GOODS_NOT_FOUND(404,"商品不存在"),
    GOODS_SAVE_ERROR(500,"新增商品失败"),
    GOODS_STOCK_NOT_FOUND(404,"商品库存不存在"),
    GOODS_SKU_NOT_FOUND(404,"商品SKU不存在"),
    GOODS_DETAIL_NOT_FOUND(404,"商品详情不存在"),

    UPLOAD_FILE_ERROR(500,"文件上传失败"),

    INVALID_FILE_TYPE(400,"无效的文件类型")


    ;

    private int code;
    private String msg;
}
