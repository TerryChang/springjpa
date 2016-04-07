package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.entity.Member;
import com.terry.springjpa.repository.MemberRepository;
import com.terry.springjpa.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Page<Member> list(Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Member> listAll() throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public Member view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Member t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Member t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		
	}

}
