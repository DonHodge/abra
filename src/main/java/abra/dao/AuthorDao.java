package abra.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import abra.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {

}