package org.smartdubai.app.repository;

import org.smartdubai.app.beans.BookClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookClassificationRepository extends JpaRepository<BookClassification, String>{

}
