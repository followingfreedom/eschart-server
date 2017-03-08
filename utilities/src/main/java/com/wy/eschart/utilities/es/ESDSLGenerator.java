package com.wy.eschart.utilities.es;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wy.eschart.bean.AggsModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangyang on 2017/3/7.
 */
public class ESDSLGenerator {


    public Map<String, Map> generateESAggsDSL(List<AggsModel> aggsList) {
        List<AggsModel> cpAggsList = Lists.newArrayList(aggsList);
        Map<String, Map> map = Maps.newHashMap();
        Map tmpMap = Maps.newHashMap();

        if (cpAggsList.size() == 1) {
            AggsModel aggsModel = cpAggsList.get(0);
            tmpMap.put(aggsModel.getName(), aggsModel.getBody());
            map.put("aggs", tmpMap);
            return map;
        }


        //无限循环，终止条件是parent=="root"
        Map allMap = Maps.newHashMap();
        while (true) {
            List<AggsModel> tmpList = cpAggsList;
            if (tmpList.size() == 1) {
                Map tMap = Maps.newHashMap();
                AggsModel tModel = tmpList.get(0);
                tMap.put(tModel.getName(), tModel.getBody());
                tMap.put("aggs", allMap);
                return tMap;
            }
            List list = getChildMap(tmpList);
            allMap = (Map) list.get(0);
            tmpList.removeAll((List) list.get(1));
        }
    }

    // 确定同一层级下的
    private List getChildMap(List<AggsModel> aggsList) {
        Map cMap = Maps.newHashMap();
        AggsModel aggsModel = getChildModel(aggsList);
        // 根据model parent,确定同一个级别有几个
        String parent = aggsModel.getParent();
        List<AggsModel> sameChildList =
                aggsList.stream().filter(model -> model.getParent().equals(parent))
                        .collect(Collectors.toList());
        sameChildList.forEach(child -> {
            cMap.put(child.getName(), child.getBody());
        });
        List list = Lists.newArrayList();
        list.add(cMap);
        list.add(sameChildList);
        return list;
    }

    // 确定最深一层
    private AggsModel getChildModel(List<AggsModel> aggsList) {
        if (aggsList.size() == 1) return aggsList.get(0); // root情况
        List<AggsModel> tmpAggsList = Lists.newArrayList(aggsList);

        for (int i = 0; i < aggsList.size(); i++) {
            AggsModel tempModel = aggsList.get(i);
            String modelP = tempModel.getParent();
            if (modelP.equals("root")) continue;

            int flag = 0;
            for (int j = 0; j < tmpAggsList.size(); j++) {
                String tParent = tmpAggsList.get(j).getParent();
                if (tParent.equals(tempModel.getName())) flag = 1;
            }
            if (flag == 0) return tempModel;
        }

        return null;
    }
}
