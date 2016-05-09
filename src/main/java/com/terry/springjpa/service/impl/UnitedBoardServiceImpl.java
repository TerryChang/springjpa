package com.terry.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.repository.UnitedBoardRepository;
import com.terry.springjpa.service.UnitedBoardService;
import com.terry.springjpa.vo.SearchVO;
import com.terry.springjpa.vo.UnitedBoardVO;

@Service
@Transactional
public class UnitedBoardServiceImpl implements UnitedBoardService {

	@Autowired
	UnitedBoardRepository repository;
	
	@Autowired
	Converter<UnitedBoard, UnitedBoardVO> unitedBoardTounitedBoardVOConverter;
	
	@Autowired
	Converter<UnitedBoardVO, UnitedBoard> unitedBoardVOToUnitedBoardConverter;
	
	@Override
	@Transactional(readOnly=true)
	public Page<UnitedBoardVO> list(SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("요청하신 메소드는 지원하지 않습니다");
	}
	
	@Transactional(readOnly=true)
	public Page<UnitedBoardVO> list(Long boardTypeIdx, SearchVO searchVO, Pageable pageable) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		return repository.selectList(boardTypeIdx, searchVO, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<UnitedBoardVO> listAll(SearchVO searchVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("요청하신 메소드는 지원하지 않습니다");
	}

	/**
	 * 통합 게시판 상세보기 작업을 진행한다
	 * 이때 주의할 점이 있다.
	 * 상세보기를 할 경우엔 조회수를 1 증가시켜야 하는데..
	 * 이를 JPA를 이용해서 진행하게 될 경우 최근수정시간까지 같이 반영이 되는 문제가 있다.
	 * 그래서 조회수 증가에 대해서는 Mybatis를 이용해서 진행하게 된다(조회수 증가는 Update이기 때문에 @Transactional의 readOnly 옵션을 true로 주지 않았다)
	 * @param idx
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws DataAccessException
	 */
	@Override
	@Transactional
	public UnitedBoardVO view(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		UnitedBoard unitedBoard = repository.findByIdx(idx);
		UnitedBoardVO result = unitedBoardTounitedBoardVOConverter.convert(unitedBoard);
		// 조회수를 1 증가시키는 작업을 진행한다(이 작업은 JPA가 아닌 Mybatis 작업이기 때문에 영속성 컨텍스트를 거치지 않고 DB를 직접 억세스한다)
		repository.updateViewCnt(idx);
		return result;
	}

	@Override
	public void insert(UnitedBoardVO unitedBoardVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		UnitedBoard unitedBoard = unitedBoardVOToUnitedBoardConverter.convert(unitedBoardVO);
		repository.saveAndFlush(unitedBoard);
	}

	@Override
	public void update(UnitedBoardVO unitedBoardVO) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		UnitedBoard unitedBoard = unitedBoardVOToUnitedBoardConverter.convert(unitedBoardVO);
		repository.saveAndFlush(unitedBoard);
	}

	@Override
	public void delete(Long idx) throws UnsupportedOperationException, DataAccessException {
		// TODO Auto-generated method stub
		repository.delete(idx);
	}
	
}
