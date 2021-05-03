package org.smartdubai.app.repository;

import org.smartdubai.app.beans.BookPromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPromoCodeRepository extends JpaRepository<BookPromoCode, String>{

}
