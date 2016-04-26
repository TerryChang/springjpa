package com.terry.springjpa.config.servlet.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.terry.springjpa.common.annotation.ExistCheck;
import com.terry.springjpa.service.CommonService;

public class ExistCheckValidator implements ConstraintValidator<ExistCheck, Object> {
	
	private String tableName;
	private String columnName;

	@Autowired
	CommonService commonService;
	
	@Override
	public void initialize(ExistCheck existCheck) {
		// TODO Auto-generated method stub
		this.tableName = existCheck.tableName();
		this.columnName = existCheck.columnName();
	}

	/**
	 * @ExistCheck 어노테이션을 통해 입력받은 테이블명, 컬럼명을 이용해서
	 * 해당 테이블에 컬럼으로 값이 존재하는지를 체크한다
	 * Injection 받은 Service 객체의 메소드에서 true를 return 했다면
	 * 이는 테이블에 존재한다는 의미이기 때문에 중복 체크 기능 관점에서 보면 중복된다는 의미여서 false를 return 하고
	 * Service 객체의 메소드에서 false를 retrun 했다면
	 * 이는 테이블에 존재하지 않는다는 의미이기 때문에 중복 체크 기능 관점에서 보면 중복되지 않는다는 의미로 true를 return 하게 해서
	 * Validate 작업을 진행하게끔 한다
	 * @param value
	 * @param context
	 * @return
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		boolean isExist = commonService.existCheck(tableName, columnName, value.toString());
		return isExist == true ? false : true;
	}

}
