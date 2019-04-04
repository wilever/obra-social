package com.mycompany.repository;

import com.mycompany.domain.ColumnModule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ColumnModule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColumnModuleRepository extends JpaRepository<ColumnModule, Long>, JpaSpecificationExecutor<ColumnModule> {

}
