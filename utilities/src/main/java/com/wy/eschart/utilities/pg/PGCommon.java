package com.wy.eschart.utilities.pg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Created by wangyang on 2017/3/7.
 */
@Component
public class PGCommon {

    @Autowired
    private ObjectMapper objectMapper;

    public PGobject getPGObject(Object originValue) throws JsonProcessingException, SQLException {
        PGobject pGobject = new PGobject();
        pGobject.setType("json");
        pGobject.setValue(objectMapper.writeValueAsString(originValue));
        return pGobject;
    }
}
