package com.community.api.repository;

import com.community.api.model.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmRepository extends JpaRepository<Dm, Long> {

}
