package com.junsu.cyr.repository;

import com.junsu.cyr.domain.empathys.Empathy;
import com.junsu.cyr.domain.empathys.EmpathyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpathyRepository extends JpaRepository<Empathy, EmpathyId> {

}
