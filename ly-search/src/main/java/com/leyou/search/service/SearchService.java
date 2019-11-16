package com.leyou.search.service;

import com.leyou.common.myexception.LyException;
import com.leyou.common.myexception.MyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.netflix.discovery.converters.Auto;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJ
 */
@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private BrandClient brandClient;

    public Goods buildGoods(Spu spu){

        //查询分类
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());

        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand == null) {
            throw new MyException(LyException.BRAND_NOT_FOUND);
        }
        //搜索字段
         String all = spu.getTitle()+ StringUtils.join(names," ")+brand.getName();

        //查询sku
        List<Sku> skuList = goodsClient.querySkuBySpuId(spu.getId());
        if (skuList == null) {
            throw new MyException(LyException.GOODS_SKU_NOT_FOUND);
        }

        List<Map<String,Object>> skus = new ArrayList<>();
        //价格集合
        List<Long> priceList = new ArrayList<>();
        for (Sku sku : skuList) {
            Map<String ,Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);
            //处理价格
            priceList.add(sku.getPrice());
        }

        //查询规格参数
        specificationClient.qu
        //查询商品详情
        //规格参数

        //构建goods
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spu.getId());
        goods.setAll(all); // 搜索字段，包含标题，分类，品牌，规格等
        goods.setPrice(priceList); // 所有sku的价格集合
        goods.setSkus(JsonUtils.serialize(skuList)); //TODO 所有sku的集合json格式
        goods.setSpecs(null); //TODO 所有的可搜索的规格参数
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }


}
