package app.hivenote.account;

import app.hivenote.account.entity.AccountEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
    extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
  Optional<AccountEntity> findByEmail(String email);

  Optional<AccountEntity> findByPasswordResetToken(String token);
}
