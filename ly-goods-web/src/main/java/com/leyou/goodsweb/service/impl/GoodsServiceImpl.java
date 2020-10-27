package com.leyou.goodsweb.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.utils.JsonUtils;
import com.leyou.goodsweb.client.BrandClient;
import com.leyou.goodsweb.client.CategoryClient;
import com.leyou.goodsweb.client.GoodsClient;
import com.leyou.goodsweb.service.GoodsService;
import com.leyou.item.Brand;
import com.leyou.item.Category;
import com.leyou.item.Sku;
import com.leyou.item.SpuDetail;
import com.leyou.item.bo.SpuBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;







    @Override
    public Map<String, Object> lodelModel(Long spuId) {


        //查询Goods对象
        SpuBo spuBo = goodsClient.queryGoodsById(spuId);
        //查询SpuDetail对象
        SpuDetail spuDetail = spuBo.getSpuDetail();
        //查询Spu对象
        //查询Sku对象 List<Sku> skus
        List<Sku> skuList = spuBo.getSkus();

        //查询Category对象 List<Category> categorys;
        List<Long> ids=new ArrayList<>();
        ids.add(spuBo.getCid1());
        ids.add(spuBo.getCid2());
        ids.add(spuBo.getCid3());
        List<Category> categoryList = categoryClient.queryCategoryByIds(ids).getBody();

        //查询brand对象 Brand

        List<Long> brandIds=new ArrayList<>();
        brandIds.add(spuBo.getBrandId());
        Brand brand = brandClient.queryBrandByIds(brandIds).get(0);

        //查询规格属性
        String allSpecJson = spuDetail.getSpecifications();
        //List<Map<String,Object>>
        List<Map<String, Object>> allSpecs = JsonUtils.nativeRead(allSpecJson, new TypeReference<List<Map<String, Object>>>() {
        });

        Map<Integer,String> specName=new HashMap<>();
        Map<Integer,Object> specValue=new HashMap<>();
        getAllSpecifications(allSpecs,specName,specValue);

        //获取特有的规格参数
        String specTJson = spuDetail.getSpecTemplate();
        Map<String,String[]> specs = JsonUtils.nativeRead(specTJson, new TypeReference<Map<String, String[]>>() {
        });
        Map<Integer,String> specialParamName = new HashMap<>();
        Map<Integer,String[]> specialParamValue = new HashMap<>();
        this.getSpecialSpec(specs,specName,specValue,specialParamName,specialParamValue);


        //按照组构造规格参数
        List<Map<String, Object>> groups = getGroupsSpec(allSpecs, specName, specValue);

        Map<String,Object> map=new HashMap();
        map.put("spu",spuBo);
        map.put("spuDetail",spuDetail);
        map.put("skus",skuList);
        map.put("brand",brand);
        map.put("categories",categoryList);
        map.put("specName",specName);
        map.put("specValue",specValue);
        map.put("groups",groups);
        map.put("specialParamName",specialParamName);
        map.put("specialParamValue",specialParamValue);




        return map;
    }

    private List<Map<String, Object>> getGroupsSpec(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        List<Map<String, Object>> groups = new ArrayList<>();
        int i = 0;
        int j = 0;
        for (Map<String,Object> spec :allSpecs){
            List<Map<String, Object>> params = (List<Map<String, Object>>) spec.get("params");
            List<Map<String,Object>> temp = new ArrayList<>();
            for (Map<String,Object> param :params) {
                for (Map.Entry<Integer, String> entry : specName.entrySet()) {
                    if (entry.getValue().equals(param.get("k").toString())) {
                        String value = specValue.get(entry.getKey()) != null ? specValue.get(entry.getKey()).toString() : "无";
                        Map<String, Object> temp3 = new HashMap<>(16);
                        temp3.put("id", ++j);
                        temp3.put("name", entry.getValue());
                        temp3.put("value", value);
                        temp.add(temp3);
                    }
                }
            }
            Map<String,Object> temp2 = new HashMap<>(16);
            temp2.put("params",temp);
            temp2.put("id",++i);
            temp2.put("name",spec.get("group"));
            groups.add(temp2);
        }
        return groups;
    }

    private void getSpecialSpec(Map<String, String[]> specs, Map<Integer, String> specName, Map<Integer, Object> specValue, Map<Integer, String> specialParamName, Map<Integer, String[]> specialParamValue) {
        if (specs != null) {
            for (Map.Entry<String, String[]> entry : specs.entrySet()) {
                String key = entry.getKey();
                for (Map.Entry<Integer,String> e : specName.entrySet()) {
                    if (e.getValue().equals(key)){
                        specialParamName.put(e.getKey(),e.getValue());
                        //因为是放在数组里面，所以要先去除两个方括号，然后再以逗号分割成数组
                        String  s = specValue.get(e.getKey()).toString();
                        String result = StringUtils.substring(s,1,s.length()-1);
                        specialParamValue.put(e.getKey(), result.split(","));
                    }
                }
            }
        }
    }

    private void getAllSpecifications(List<Map<String, Object>> allSpecs, Map<Integer, String> specName, Map<Integer, Object> specValue) {
        String k = "k";
        String v = "v";
        String unit = "unit";
        String numerical = "numerical";
        String options = "options";
        int i = 0;
        if (allSpecs != null) {
            for (Map<String, Object> s : allSpecs) {
                List<Map<String, Object>> params = (List<Map<String, Object>>) s.get("params");
                for (Map<String, Object> param : params) {
                    String result;
                    if (param.get(v) == null) {
                        result = "无";
                    } else {
                        result = param.get(v).toString();
                    }
                    if (param.containsKey(numerical) && (boolean) param.get(numerical)) {
                        if (result.contains(".")) {
                            Double d = Double.valueOf(result);
                            if (d.intValue() == d) {
                                result = d.intValue() + "";
                            }
                        }
                        i++;
                        specName.put(i, param.get(k).toString());
                        specValue.put(i, result + param.get(unit).toString());
                    } else if (param.containsKey(options)) {
                        i++;
                        specName.put(i, param.get(k).toString());
                        specValue.put(i, param.get(options));
                    } else {
                        i++;
                        specName.put(i, param.get(k).toString());
                        specValue.put(i, param.get(v));
                    }
                }
            }
        }
    }
}
