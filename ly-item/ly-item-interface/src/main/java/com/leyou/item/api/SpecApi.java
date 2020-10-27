package com.leyou.item.api;


import com.leyou.item.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("spec")
public interface SpecApi {


    /**
     * 查询商品分类对应的规格参数模板
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Long id);

    /**
     * 保存一个规格参数模板
     * @param specification
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveSpecification(Specification specification);
    /**
     * 修改一个规格参数模板
     * @param specification
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateSpecification(Specification specification);

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpecification(@PathVariable("id") Long id);
}