package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;

import com.leyou.item.Sku;
import com.leyou.item.SpuDetail;
import com.leyou.item.bo.SpuBo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("goods")
public interface GoodsApi {


    /**
     * 分页查询
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @param saleable
     * @return
     */
    @GetMapping("/spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable);

    /**
     * 保存商品
     * @param spu
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spu);

    /**
     * 修改商品
     * @param spuBo
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo);
    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    @GetMapping("/spu/{id}")
    public SpuBo queryGoodsById(@PathVariable("id") Long id);

    /**
     * 根据Spu的id查询其下所有的sku
     * @param id
     * @return
     */
    @GetMapping("sku/list/{id}")
    public List<Sku> querySkuBySpuId(@PathVariable("id") Long id);

    /**
     * 根据spu商品id查询详情
     * @param id
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id);

    /**
     * 商品上下架
     * @param ids
     * @return
     */
    @PutMapping("/spu/out/{id}")
    public ResponseEntity<Void> goodsSoldOut(@PathVariable("id") String ids);

    /**
     * 删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") String ids);

    @GetMapping("/sku/{id}")
    public Sku querySkuById(@PathVariable("id") Long id);


}
