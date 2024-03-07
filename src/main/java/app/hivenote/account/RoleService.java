package app.hivenote.account;

import app.hivenote.account.entity.Role;
import app.hivenote.account.entity.RoleEntity;
import app.hivenote.exception.ApiException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class RoleService {
  private final RoleRepository repository;

  public RoleService(RoleRepository repository) {
    this.repository = repository;
  }

  public List<RoleEntity> findAll() {
    return repository.findAll();
  }

  public Optional<RoleEntity> findById(UUID id) {
    return repository.findById(id);
  }

  public RoleEntity findByName(Role name) throws ApiException {
    return repository
        .findByName(name)
        .orElseThrow(() -> ApiException.notFound("err.role.dont.exist"));
  }
}
