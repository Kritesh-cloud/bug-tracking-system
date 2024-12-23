package cm.ex.bug.repository;

import cm.ex.bug.entity.Comment;
import cm.ex.bug.entity.DataHolder;
import cm.ex.bug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataHolderRepository extends JpaRepository<DataHolder, UUID> {


    Optional<DataHolder> findByNameAndType(String name, String type);

    List<DataHolder> findByType(String type);

    @Query(nativeQuery = true, value = "SELECT dh FROM data_holder dh WHERE dh.type = :type ORDER BY dh.level ASC")
    List<DataHolder> findByTypeAsc(String type);

}
