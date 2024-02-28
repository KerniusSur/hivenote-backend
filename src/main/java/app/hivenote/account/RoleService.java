package app.hivenote.account;

import app.hivenote.account.entity.Role;
import app.hivenote.account.entity.RoleEntity;
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

  public Optional<RoleEntity> findByName(Role name) {
    return repository.findByName(name);
  }
}
