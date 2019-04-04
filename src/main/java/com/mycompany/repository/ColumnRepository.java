package com.mycompany.repository;

import com.mycompany.domain.Column;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Column entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColumnRepository extends JpaRepository<Column, Long>, JpaSpecificationExecutor<Column> {

}
