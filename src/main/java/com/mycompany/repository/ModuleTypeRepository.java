package com.mycompany.repository;

import com.mycompany.domain.ModuleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ModuleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModuleTypeRepository extends JpaRepository<ModuleType, Long>, JpaSpecificationExecutor<ModuleType> {

}
