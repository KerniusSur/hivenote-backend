package com.hivenote.backend.account;

import com.hivenote.backend.account.entity.Role;
import com.hivenote.backend.account.entity.RoleEntity;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByName(Role name);
}
