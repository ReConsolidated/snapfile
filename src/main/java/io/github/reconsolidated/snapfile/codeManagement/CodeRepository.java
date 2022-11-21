package io.github.reconsolidated.snapfile.codeManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<CodeInstance, String> {
    Optional<CodeInstance> findByCode(String code);

    List<CodeInstance> findAllByOwnerSessionId(String ownerSessionId);

    Optional<CodeInstance> findByCodeAndOwnerSessionId(String code, String ownerSessionId);
}
