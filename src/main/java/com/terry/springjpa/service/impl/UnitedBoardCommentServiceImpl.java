package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.entity.UnitedBoardComment;
import com.terry.springjpa.repository.UnitedBoardCommentRepository;
import com.terry.springjpa.service.UnitedBoardCommentService;

@Service
@Transactional
public class UnitedBoardCommentServiceImpl implements UnitedBoardCommentService {

	@Autowired
	UnitedBoardCommentRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Page<UnitedBoardComment> list(Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<UnitedBoardComment> listAll() throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public UnitedBoardComment view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(UnitedBoardComment t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(UnitedBoardComment t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

}
