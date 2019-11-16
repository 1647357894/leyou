package com.leyou.item.mapper;


import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * 使用通用mapper
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
}
