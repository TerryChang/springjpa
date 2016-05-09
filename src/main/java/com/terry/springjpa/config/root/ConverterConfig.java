package com.terry.springjpa.config.root;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import com.terry.springjpa.common.converter.BoardTypeToBoardTypeVOConverter;
import com.terry.springjpa.common.converter.BoardTypeVOToBoardTypeConverter;
import com.terry.springjpa.common.converter.MemberToMemberVOConverter;
import com.terry.springjpa.common.converter.MemberVOToMemberConverter;
import com.terry.springjpa.common.converter.UnitedBoardToUnitedBoardVOConverter;
import com.terry.springjpa.common.converter.UnitedBoardVOToUnitedBoardConverter;
import com.terry.springjpa.entity.BoardType;
import com.terry.springjpa.entity.Member;
import com.terry.springjpa.entity.UnitedBoard;

@Configuration
public class ConverterConfig {

	/**
	 * BoardType Entity 클래스 객체를 BoardTypeVO 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @return
	 */
	@Bean
	public BoardTypeToBoardTypeVOConverter boardTypeToBoardTypeVOConverter(){
		return new BoardTypeToBoardTypeVOConverter();
		
	}
	
	/**
	 * BoardTypeVO 클래스 객체를 BoardType 엔티티 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @param repository BoardType 엔티티 관련 Spring Data JpaRepository 인터페이스를 구현한 bean 객체
	 * @return
	 */
	@Bean
	public BoardTypeVOToBoardTypeConverter boardTypeVOToBoardTypeConverter(JpaRepository<BoardType, Long> repository){
		return new BoardTypeVOToBoardTypeConverter(repository);
	}
	
	/**
	 * Member Entity 클래스 객체를 MemberVO 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @return
	 */
	@Bean
	public MemberToMemberVOConverter memberToMemberVOConverter(){
		return new MemberToMemberVOConverter();
	}
	
	/**
	 * MemberVO 클래스 객체를 Member 엔티티 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @param repository Member 엔티티 관련 Spring Data JpaRepository 인터페이스를 구현한 bean 객체
	 * @return
	 */
	@Bean
	public MemberVOToMemberConverter memberVOToMemberConverter(JpaRepository<Member, Long> repository){
		return new MemberVOToMemberConverter(repository);
	}
	
	/**
	 * UnitedBoard Entity 클래스 객체를 UnitedBoardVO 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @return
	 */
	@Bean
	public UnitedBoardToUnitedBoardVOConverter unitedBoardToUnitedBoardrVOConverter(EntityManager em){
		return new UnitedBoardToUnitedBoardVOConverter(em);
	}
	
	/**
	 *  UnitedBoardVO 클래스 객체를 UnitedBoard 엔티티 클래스 객체로 변환하는 Converter 클래스 객체를 bean으로 등록한다
	 * @param unitedBoardRepository UnitedBoard 엔티티 관련 Spring Data JpaRepository 인터페이스를 구현한 bean 객체
	 * @param boardTypeRepository BoardType 엔티티 관련 Spring Data JpaRepository 인터페이스를 구현한 bean 객체(UnitedBoard 엔티티의 멤버변수로 BoardType 엔티티 멤버변수가 있기 때문에 이를 사용)
	 * @param memberRepository Member 엔티티 관련 Spring Data JpaRepository 인터페이스를 구현한 bean 객체(UnitedBoard 엔티티의 멤버변수로 Member 엔티티 멤버변수가 있기 때문에 이를 사용)
	 * @return
	 */
	@Bean
	public UnitedBoardVOToUnitedBoardConverter unitedBoardVOToUnitedBoardrConverter(JpaRepository<UnitedBoard, Long> unitedBoardRepository, JpaRepository<BoardType, Long> boardTypeRepository, JpaRepository<Member, Long> memberRepository){
		return new UnitedBoardVOToUnitedBoardConverter(unitedBoardRepository, boardTypeRepository, memberRepository);
	}
}
