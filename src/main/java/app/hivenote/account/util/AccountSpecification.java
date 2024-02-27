package app.hivenote.account.util;

import app.hivenote.utils.SpecificationUtil;
import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.Role;
import jakarta.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {
  public static Specification<AccountEntity> hasEqualRole(Long roleId) {
    return (root, query, cb) -> cb.equal(root.join("roles").join("role").get("id"), roleId);
  }

  public static Specification<AccountEntity> hasEqualRole(Role role) {
    return (root, query, cb) -> cb.equal(root.join("roles").join("role").get("name"), role);
  }

  public static Specification<AccountEntity> hasEqualTopic(Long topicId) {
    return (root, query, cb) -> cb.equal(root.join("topics").join("topic").get("id"), topicId);
  }

  public static Specification<AccountEntity> isInDepartment(Long departmentId) {
    // TODO: fix this. This searches for accounts (managers), which manage department with given id
    return (root, query, cb) ->
        cb.equal(root.join("departments").join("department").get("id"), departmentId);
  }

  public static Specification<AccountEntity> getSpecificationForFilteringBy(
      @Nullable Long roleId,
      @Nullable Long topicId,
      @Nullable Long departmentId,
      @Nullable Role role) {
    List<Specification<AccountEntity>> specifications = new ArrayList<>();

    if (roleId != null) {
      specifications.add(hasEqualRole(roleId));
    }
    if (topicId != null) {
      specifications.add(hasEqualTopic(topicId));
    }
    if (departmentId != null) {
      specifications.add(isInDepartment(departmentId));
    }
    if (role != null) {
      specifications.add(hasEqualRole(role));
    }
    if (specifications.size() > 0) {
      return SpecificationUtil.toANDSpecification(specifications);
    }
    return null;
  }
}
