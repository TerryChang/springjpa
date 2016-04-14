package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.repository.UnitedBoardRepository;
import com.terry.springjpa.service.UnitedBoardService;
import com.terry.springjpa.vo.SearchVO;

@Service
@Transactional
public class UnitedBoardServiceImpl implements UnitedBoardService {

	@Autowired
	UnitedBoardRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Page<UnitedBoard> list(SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<UnitedBoard> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public UnitedBoard view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(UnitedBoard t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(UnitedBoard t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}
	
}
