package com.focowell.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.focowell.model.VirtualTableSequence;;

@Repository
public interface VirtualTableSequenceDao extends CrudRepository<VirtualTableSequence, Long> {
	Optional<VirtualTableSequence> findBySequenceName(String sequenceName);
}
