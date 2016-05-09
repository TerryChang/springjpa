package com.terry.springjpa.repository.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Projections;
import com.terry.springjpa.common.annotation.FirstEntityManagerFlush;
import com.terry.springjpa.entity.QBoardType;
import com.terry.springjpa.entity.QMember;
import com.terry.springjpa.entity.QUnitedBoard;
import com.terry.springjpa.entity.UnitedBoard;
import com.terry.springjpa.repository.UnitedBoardCustom;
import com.terry.springjpa.vo.SearchVO;
import com.terry.springjpa.vo.UnitedBoardVO;

public class UnitedBoardRepositoryImpl extends QueryDslRepositorySupport implements UnitedBoardCustom {
	
	@Autowired
	SqlSessionTemplate sqlSession;

	public UnitedBoardRepositoryImpl() {
		super(UnitedBoard.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Page<UnitedBoardVO> selectList(Long boardTypeIdx, SearchVO searchVO, Pageable pageable) {
		// TODO Auto-generated method stub
		QUnitedBoard unitedBoard = QUnitedBoard.unitedBoard;
		QBoardType boardType = QBoardType.boardType;
		QMember member = QMember.member;
		
		String searchCnd = searchVO.getSearchCnd();			// 검색종류
		String searchWrd = searchVO.getSearchWrd();			// 검색어
		
		JPQLQuery query = from(unitedBoard);
		
		/*
		query = query.innerJoin(unitedBoard.boardType, boardType).fetch()
				.innerJoin(unitedBoard.member, member).fetch();
		*/
		
		if(boardTypeIdx != null){
			query = query.where(unitedBoard.boardType.idx.eq(boardTypeIdx));
		}
		
		if(StringUtils.hasText(searchWrd)){				// 검색어가 있으면
			if(searchCnd.equals("")){					// 전체 검색
				query = query.where((unitedBoard.member.loginId.eq(searchWrd)).or(unitedBoard.title.contains(searchWrd)).or(unitedBoard.contents.contains(searchWrd)));
			}else if(searchCnd.equals("loginId")){			// 아이디 검색
				query = query.where(unitedBoard.member.loginId.eq(searchWrd));
				// query = query.where(unitedBoard.member.idx.eq(1L));
			}else if(searchCnd.equals("title")){			// 제목 검색
				query = query.where(unitedBoard.title.contains(searchWrd));
			}else if(searchCnd.equals("contents")){			// 내용 검색
				query = query.where(unitedBoard.contents.contains(searchWrd));
			}
		}
		
		query = getQuerydsl().applyPagination(pageable, query);
		
		// Projections 클래스에서 제공되는 static 메소드를 이용해서 작업할 경우 fetch join을 하지 말고 작업하도록 한다
		// fetch join을 해야 하는 것이라 생각해서 그렇게 작업했는데 에러가 발생하고 fetch join을 하지 않으니 정상 동작을 했다
		// 그래서 위에서 fetch join을 한 부분도 주석처리 했다
		List<UnitedBoardVO> contentPagingResult = query.list(Projections.bean(UnitedBoardVO.class, unitedBoard.idx, unitedBoard.boardType.boardTypeName, unitedBoard.title, unitedBoard.member.loginId.as("memberLoginId"), unitedBoard.viewCnt, unitedBoard.insertUpdateDT.insertDateTime));
		long count = query.count();
		
		PageImpl<UnitedBoardVO> result = new PageImpl<UnitedBoardVO>(contentPagingResult, pageable, count);
		
		return result;
	}

	/**
	 * 게시물 조회수를 1 증가시키는 메소드이다.
	 * Mybatis를 사용하기 때문에 @FirstEntityManagerFlush 어노테이션을 붙여서 이 메소드를 호출하기 전에 영속성 컨켁스트에 작업했던것을 DB에 반영하도록 했다
	 * 고민한 것은 Mybatis를 이용한 Update이다 보니 영속성 컨텍스트를 거치지 않고 DB를 직접 억세스 하는 작업이어서..
	 * 이렇게 작업할 경우 영속성 컨텍스트에 이 Update 작업이 반영이 안된 상태가 된다.
	 * 이로 인해 원래는 이 메소드를 호출한 뒤에 EntityManager의 clear 메소드를 이용해서 초기화를 시켜야 하지만 이렇게 할 경우 뒤에 이어서 영속성 컨텍스트를 이용해서 작업하는 것에 대해서는..
	 * 기존에 영속성 컨텍스트가 가지고 있던 엔티티 및 이와 연관된 작업들이 모두 날라간 뒤에 다시 작업을 하게 되어서 문제의 소지가 있다
	 * 그래서 영속성 컨텍스트 초기화에 대해선 이 메소드를 호출한 쪽에서 초기화를 제어해주는 것이 나을것 같아서 @FirstEntityManagerFlush 같은 별도의 AOP 작업을 설정하는 것은 없게끔 했다
	 */
	@FirstEntityManagerFlush
	@Override
	public void updateViewCnt(Long idx) {
		// TODO Auto-generated method stub
		sqlSession.update("unitedBoard.updateViewCnt", idx);
	}

	
}
