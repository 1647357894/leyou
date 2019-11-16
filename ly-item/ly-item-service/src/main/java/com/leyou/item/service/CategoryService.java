package com.leyou.item.service;

import com.leyou.common.myexception.LyException;
import com.leyou.common.myexception.MyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 没写接口。。。
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryListByPid(Long pid){
        Category category = new Category();
        category.setParentId(pid);
        //将不为空的字段作为查询条件
       return categoryMapper.select(category);
    }

    public List<Category> queryByIds(List<Long> ids){
        List<Category> categories = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(categories)){
            throw new MyException(LyException.CATEGORY_NOT_FOUND);
        }
        return categories;
    }
}
