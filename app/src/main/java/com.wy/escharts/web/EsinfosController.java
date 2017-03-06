package com.wy.escharts.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wy.eschart.bean.AggsModel;
import com.wy.eschart.model.ChartInfosDOMapper;
import com.wy.eschart.model.EsinfosDOMapper;
import com.wy.eschart.model.chartInfos.ChartInfosDO;
import com.wy.eschart.model.esinfos.EsinfosDO;
import org.postgresql.util.PGobject;
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
    private ObjectMapper objectMapper;

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
        chartInfosDOMapper.insert(chartInfosDO);

        List<AggsModel> models = entry.getValue();
        models.forEach(model -> {
            EsinfosDO esinfosDO = new EsinfosDO();
            try {
                esinfosDO.setAggs(getPGObject(model));
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

    private PGobject getPGObject(Object originValue) throws JsonProcessingException, SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(objectMapper.writeValueAsString(originValue));
        return pGobject;
    }
}
