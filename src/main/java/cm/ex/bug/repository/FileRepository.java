package cm.ex.bug.repository;

import cm.ex.bug.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM file ORDER BY created_at DESC LIMIT 1")
    Optional<File> findLastFile();
}
