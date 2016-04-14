package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.terry.springjpa.entity.Member;
import com.terry.springjpa.repository.MemberRepository;
import com.terry.springjpa.service.MemberService;
import com.terry.springjpa.vo.SearchVO;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberRepository repository;
	
	@Override
	@Transactional(readOnly=true)
	public Page<Member> list(SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		Page<Member> result = null;
		
		String searchCnd = searchVO.getSearchCnd();
		if(!StringUtils.hasText(searchCnd)){
			result = repository.findAll(pageable);
		}else{
			if(searchVO.getSearchCnd().equals("1")){			// loginid
				result = repository.findByLoginIdContaining(searchVO.getSearchWrd(), pageable);
			}else if(searchVO.getSearchCnd().equals("2")){		// 이름으로 검색
				result = repository.findByNameContaining(searchVO.getSearchWrd(), pageable);
			}
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Member> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("요청하신 메소드는 지원하지 않습니다");
	}

	@Override
	@Transactional(readOnly=true)
	public Member view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return repository.findOne(idx);
	}

	@Override
	public void insert(Member t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.saveAndFlush(t);
	}

	@Override
	public void update(Member t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		Member member = repository.findOne(t.getIdx());
		member.entityUpdate(t);
		repository.saveAndFlush(member);
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.delete(idx);
	}

}
