package com.wy.escharts.web;

import com.wy.eschart.bean.AggsModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangyang on 2017/3/1.
 */
@RestController
@RequestMapping("/esinfos")
public class EsinfosController {

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=UTF-8")
    public int insertEsinfos(@RequestBody List<AggsModel> aggsModelList) {

        return 0;
    }

    @GetMapping
    public String test() {
        return "test";
    }
}
