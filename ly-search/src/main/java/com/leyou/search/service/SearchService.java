package com.leyou.search.service;


import com.leyou.item.Spu;
import com.leyou.search.bo.SearchRequest;
import com.leyou.search.pojo.Goods;
import com.leyou.search.vo.SearchResult;
import java.io.IOException;

public interface SearchService {

    /**
     * 查询商品信息
     * @param spu
     * @return
     * @throws IOException
     */
    Goods buildGoods(Spu spu) throws IOException;

    /**
     * 商品搜索
     * @param searchRequest
     * @return
     */
    SearchResult<Goods> search(SearchRequest searchRequest);


    /**
     * 根据goods的id创建相应的索引
     * @param id
     * @throws IOException
     */
    void createIndex(Long id) throws IOException;

    /**
     * 根据goods的id删除相应的索引
     * @param id
     */
    void deleteIndex(Long id);


}
