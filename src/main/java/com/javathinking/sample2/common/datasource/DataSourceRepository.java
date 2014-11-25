package com.javathinking.sample2.common.datasource;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Date: 31/07/13
 */
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {
    DataSource findByType(String type);
}
