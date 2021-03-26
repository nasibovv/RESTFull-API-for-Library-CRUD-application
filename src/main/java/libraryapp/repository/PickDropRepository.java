package libraryapp.repository;

import libraryapp.model.PickDrop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickDropRepository extends JpaRepository<PickDrop, Long> {
}
