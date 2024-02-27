package app.hivenote.account;

import app.hivenote.account.entity.AccountEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository
    extends JpaRepository<AccountEntity, Long>, JpaSpecificationExecutor<AccountEntity> {
  Optional<AccountEntity> findByEmail(String email);
  @Query(value = ("SELECT DISTINCT a.* FROM account a LEFT JOIN appointment ap ON a.id = ap.patient_id WHERE CAST(a.user_info ->> 'createdById' AS INTEGER) = :specialistId OR ap.specialist_id = :specialistId"), nativeQuery = true)
  Optional<List<AccountEntity>> findCreatedClients(Long specialistId);

  Optional<AccountEntity> findByPasswordResetToken(String token);
}
