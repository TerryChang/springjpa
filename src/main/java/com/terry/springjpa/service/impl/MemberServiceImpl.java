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
import com.terry.springjpa.vo.MemberVO;
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
		String searchWrd = searchVO.getSearchWrd() == null ? "" : searchVO.getSearchWrd();
		
		if(!StringUtils.hasText(searchWrd)){				// 검색어가 없으면
			result = repository.findAll(pageable);
		}else{											// 검색어가 있으면
			if(searchCnd.equals("0")){					// loginId or 이름 검색
				result = repository.findByLoginIdContainingOrNameContaining(searchWrd, searchWrd, pageable);
			}else if(searchCnd.equals("1")){			// loginId 검색
				result = repository.findByLoginIdContaining(searchWrd, pageable);
			}else if(searchCnd.equals("2")){			// 이름으로 검색
				result = repository.findByNameContaining(searchWrd, pageable);
			}
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<MemberVO> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
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
	public void insert(MemberVO memberVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		Member member = memberVO.convertToEntity(repository);
		repository.saveAndFlush(member);
	}

	@Override
	public void update(MemberVO memberVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		Member member = memberVO.convertToEntity(repository);
		repository.saveAndFlush(member);
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.delete(idx);
	}

}
