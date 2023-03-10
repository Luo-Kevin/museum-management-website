package ca.mcgill.ecse321.museum.dao;

import ca.mcgill.ecse321.museum.model.Manager;
import org.springframework.data.repository.CrudRepository;

/**
 * This is the repository for the Manager class
 *
 * @author VZ
 */

public interface ManagerRepository extends CrudRepository<Manager, Long> {

  Manager findManagerByMuseumUserId(Long museumUserId);

  Manager findManagerByEmail(String email);

  Manager findManagerByName(String name);
}
