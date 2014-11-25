package com.javathinking.sample2.common.pipeline;

import javax.persistence.EntityManager;
import java.util.Map;

public class NativeSqlPipelineTask implements PipelineTask {
    private String[] sql;
    private EntityManager em;
    private String name;

    public NativeSqlPipelineTask(EntityManager em, String name,String... sql) {
        this.sql = sql;
        this.em = em;
        this.name = name;
    }

    @Override
    public boolean execute(PipelineContext context, Map data) throws Exception {
        for (String s : sql) {
            em.createNativeQuery(s).executeUpdate();
        }
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
