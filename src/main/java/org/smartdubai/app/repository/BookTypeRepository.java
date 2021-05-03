package org.smartdubai.app.repository;

import org.smartdubai.app.beans.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, String>{

}
