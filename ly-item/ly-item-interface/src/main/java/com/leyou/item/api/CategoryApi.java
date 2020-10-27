package com.leyou.item.api;


import com.leyou.item.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据父节点查询商品类目
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam("pid") Long pid);

    /**
     * 用于修改品牌信息时，商品分类信息的回显
     * @param bid
     * @return
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid);


    /**
     * 保存
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(Category category);
    /**
     * 更新
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category);

    /**
     * 删除
     * @return
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id);

    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids")List<Long> ids);

    /**
     * 根据分类id集合查询分类名称
     * @param ids
     * @return
     */
    @GetMapping("all")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids")List<Long> ids);

    /**
     * 根据分类id集合查询分类名称
     * @param id
     * @return
     */
    @GetMapping("all/level/{cid3}")
    public ResponseEntity<List<Category>> queryAllCategoryLevelByCid3(@PathVariable("cid3")Long id);

}
