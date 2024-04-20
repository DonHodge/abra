package abra.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import abra.entity.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}