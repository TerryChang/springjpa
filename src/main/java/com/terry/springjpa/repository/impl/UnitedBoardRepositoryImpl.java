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
	 * 이렇게 작업할 경우 Mybatis를 사용한 Update 한 작업이 영속성 컨텍스트에서는 본인이 직접 반영시켜주지 않는 한에는 자동으로 반영이 되질 않는다.
	 * 이러한 문제 땜에 Mybatis를 사용한 Update 작업(이 작업은 내부적으로 Update 작업이 들어가기 전에 EntityManager 의 flush 메소드를 호출하여 DB 작업을 Mybatis가 하기 전에 
	 * 영속성 컨텍스트의 작업 내용을 DB에 반영시켜 동기화를 맞춰준다)을 한 뒤에..
	 * 영속성 컨텍스트를 초기화(EntityManager 의 clear 메소드)작업을 거쳐 해당 엔티티에 대한 작업을 다시 하고자 할때 다시 DB에서 조회하게끔 하는 것이 맞지만..
	 * 영속성 컨텍스트 초기화까지 AOP에다가 반영할 경우 초기화가 빈번하게 발생할 소지가 있다.
	 * 그리고 Mybatis 작업을 한 뒤에 영속성 컨텍스트에 대한 그 어떤 작업을 할 일이 없다면 초기화 작업을 할 필요도 없다.
	 * 그래서 영속성 컨텍스트의 초기화는 AOP에서 하지 않고 이 메소드를 호출한 쪽에서 초기화를 제어해주는 것이 나을것 같아서 
	 * @FirstEntityManagerFlush 같은 별도의 AOP 작업에서 EntityManager 클래스의 clear 메소드를 호출하지는 않는것으로 방향을 잡았다
	 */
	@FirstEntityManagerFlush
	@Override
	public void updateViewCnt(Long idx) {
		// TODO Auto-generated method stub
		sqlSession.update("unitedBoard.updateViewCnt", idx);
	}
}
