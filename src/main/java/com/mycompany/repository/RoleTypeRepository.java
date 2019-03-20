package com.mycompany.repository;

import com.mycompany.domain.RoleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {

}
