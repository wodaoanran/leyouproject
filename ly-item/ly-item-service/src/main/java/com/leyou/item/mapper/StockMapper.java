package com.leyou.item.mapper;

import com.leyou.item.Stock;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface StockMapper extends Mapper<Stock>, SelectByIdListMapper<Stock,Long> {
}
