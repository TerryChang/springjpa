package com.terry.springjpa.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.repository.BoardTypeRepository;
import com.terry.springjpa.service.BoardTypeService;
import com.terry.springjpa.vo.BoardTypeVO;
import com.terry.springjpa.vo.SearchVO;

/**
 * 게시판 타입 관련 서비스 클래스이다.
 * 이 클래스의 경우에는 목록이 페이징을 할 상황이 없기 때문에 페이징 관련 메소드는 UnsupportedOperationException 예외를 던지도록 한다.
 * @author TerryChang
 *
 */
@Service
@Transactional
public class BoardTypeServiceImpl extends AbstractService<BoardTypeVO, BoardType> implements BoardTypeService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BoardTypeRepository repository;
	
	@Autowired
	Converter<BoardType, BoardTypeVO> boardTypeToBoardTypeVOConverter;
	
	@Autowired
	Converter<BoardTypeVO, BoardType> boardTypeVOToBoardTypeConverter;

	@Override
	@Transactional(readOnly=true)
	public Page<BoardTypeVO> list(SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("요청하신 메소드는 지원하지 않습니다");
	}

	@Override
	@Transactional(readOnly=true)
	public List<BoardTypeVO> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		List<BoardType> entityResult = repository.findAll();
		List<BoardTypeVO> result = convertEntityListToVOList(entityResult, boardTypeToBoardTypeVOConverter);
 		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public BoardTypeVO view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		BoardType boardType = repository.findOne(idx);
		BoardTypeVO result = boardTypeToBoardTypeVOConverter.convert(boardType);
		return result;
	}

	@Override
	public void insert(BoardTypeVO boardTypeVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		BoardType boardType = boardTypeVOToBoardTypeConverter.convert(boardTypeVO);
		repository.saveAndFlush(boardType);
	}

	@Override
	public void update(BoardTypeVO boardTypeVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		BoardType updateBoardType = boardTypeVOToBoardTypeConverter.convert(boardTypeVO);
		repository.saveAndFlush(updateBoardType);
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.delete(idx);
	}

	

}
