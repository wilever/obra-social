package com.mycompany.repository;

import com.mycompany.domain.RoleList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RoleList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleListRepository extends JpaRepository<RoleList, Long> {

    @Query("select role_list from RoleList role_list where role_list.user.login = ?#{principal.username}")
    List<RoleList> findByUserIsCurrentUser();

}
