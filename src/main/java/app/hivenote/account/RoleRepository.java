package app.hivenote.account;

import app.hivenote.account.entity.Role;
import app.hivenote.account.entity.RoleEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByName(Role name);
}
