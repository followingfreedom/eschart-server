package com.wy.escharts.web;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wy.eschart.bean.AggsModel;
import com.wy.eschart.model.ChartInfosDOMapper;
import com.wy.eschart.model.EsinfosDOMapper;
import com.wy.eschart.model.chartInfos.ChartInfosDO;
import com.wy.eschart.model.esinfos.EsinfosDO;
import com.wy.eschart.utilities.es.ESDSLGenerator;
import com.wy.eschart.utilities.es.HttpClientTools;
import com.wy.eschart.utilities.pg.PGCommon;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/1.
 */
@RestController
@RequestMapping("/esinfos")
public class EsinfosController {

    private static Logger logger = LoggerFactory.getLogger(EsinfosController.class);

    @Autowired
    private PGCommon pgCommon;

    @Autowired
    private HttpClientTools httpClientTools;

    @Autowired
    private EsinfosDOMapper esinfosDOMapper;

    @Autowired
    private ChartInfosDOMapper chartInfosDOMapper;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
    @Transactional
    public void insertEsinfos(@RequestBody Map<String, List<AggsModel>> aggs) {
        Map.Entry<String, List<AggsModel>> entry = aggs.entrySet().iterator().next();
        final String chartName = entry.getKey();

        ChartInfosDO chartInfosDO = new ChartInfosDO();
        chartInfosDO.setName(chartName);

        List<AggsModel> models = entry.getValue();

        ESDSLGenerator esdslGenerator = new ESDSLGenerator();
        Map dslAggMap = esdslGenerator.generateESAggsDSL(models);

        List list = null;
        try {
            list = httpClientTools.post("http://localhost:9200/cars/transactions/_search",
                    JSONObject.toJSONString(dslAggMap), ContentType.APPLICATION_JSON, 6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            chartInfosDO.setDslaggs(pgCommon.getPGObject(dslAggMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chartInfosDOMapper.insert(chartInfosDO);

        models.forEach(model -> {
            EsinfosDO esinfosDO = new EsinfosDO();
            try {
                esinfosDO.setAggs(pgCommon.getPGObject(model));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            esinfosDO.setNameid(chartInfosDO.getId());
            esinfosDO.setCtime(new Date());
            esinfosDO.setMtime(new Date());
            esinfosDOMapper.insert(esinfosDO);
        });

    }

    @GetMapping
    public String test() {
        return "test";
    }

}
