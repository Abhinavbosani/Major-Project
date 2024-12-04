package com.ns01.ns01.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ns01.ns01.model.EndpointsModel;

@Repository
public interface EndPointRepository extends JpaRepository<EndpointsModel,Integer> {
    
}
