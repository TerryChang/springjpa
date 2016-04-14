package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.repository.BoardTypeRepository;
import com.terry.springjpa.service.BoardTypeService;
import com.terry.springjpa.vo.SearchVO;

/**
 * 게시판 타입 관련 서비스 클래스이다.
 * 이 클래스의 경우에는 목록이 페이징을 할 상황이 없기 때문에 페이징 관련 메소드는 UnsupportedOperationException 예외를 던지도록 한다.
 * @author TerryChang
 *
 */
@Service
@Transactional
public class BoardTypeServiceImpl implements BoardTypeService {
	
	@Autowired
	BoardTypeRepository repository;

	@Override
	@Transactional(readOnly=true)
	public Page<BoardType> list(SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("요청하신 메소드는 지원하지 않습니다");
	}

	@Override
	@Transactional(readOnly=true)
	public List<BoardType> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public BoardType view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return repository.findOne(idx);
	}

	@Override
	public void insert(BoardType t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.saveAndFlush(t);
	}

	@Override
	public void update(BoardType t) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		BoardType updateBoardType = view(t.getIdx()); // update 대상이 되는 엔티티 객체를 먼저 조회한다.
		updateBoardType.entityUpdate(t);
		repository.saveAndFlush(updateBoardType);
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.delete(idx);
	}

	

}
