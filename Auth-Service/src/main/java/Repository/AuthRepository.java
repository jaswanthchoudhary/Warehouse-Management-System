package Repository;

import Model.InventoryUsers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository extends JpaRepository<InventoryUsers, Long> {

	

	Optional<InventoryUsers> findByUsername(String username);

}
