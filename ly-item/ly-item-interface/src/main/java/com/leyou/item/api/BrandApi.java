package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;


import com.leyou.item.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("brand")
public interface BrandApi {


    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public PageResult<Brand> queryBrandByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                              @RequestParam(value = "sortBy", required = false) String sortBy,
                                              @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                              @RequestParam(value = "key", required = false) String key);

    /**
     * 品牌新增
     * @param brand
     * @param categories
     * @return
     */
    @PostMapping
    public void  saveBrand(Brand brand, @RequestParam("categories") List<Long> categories);

    /**
     * 品牌修改
     * @param brand
     * @param categories
     * @return
     */
    @PutMapping
    public void updateBrand(Brand brand,@RequestParam("categories") List<Long> categories);

    /**
     * 删除tb_category_brand中的数据
     * @param bid
     * @return
     */
    @DeleteMapping("cid_bid/{bid}")
    public void deleteByBrandIdInCategoryBrand(@PathVariable("bid") Long bid);


    /**
     * 删除tb_brand中的数据,单个删除、多个删除二合一
     * @param bid
     * @return
     */
    @DeleteMapping("bid/{bid}")
    public void deleteBrand(@PathVariable("bid") String bid);

    /**
     * 根据category的id查询品牌信息
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public List<Brand> queryBrandByCategoryId(@PathVariable("cid") Long cid);

    /**
     * 根据品牌id结合，查询品牌信息
     * @param ids
     * @return
     */
    @GetMapping("list")
    public List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);

}
