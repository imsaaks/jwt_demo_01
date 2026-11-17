# JWT Token

This project provides utilities for generating and validating JSON Web Tokens (JWTs) using the io.jsonwebtoken library. JWTs are a compact, URL-safe means of representing claims to be transferred between two parties. They are commonly used for authentication and authorization purposes in web applications.

## 환경
Mysql, JAVA 17, Spring Boot 4.1.2, Swagger, MyBatis

## UI
http://localhost:8000/swagger-ui/index.html

## mysql table

CREATE TABLE IF NOT EXISTS `T_MEMBER_MST` (
`MEMBER_ID` VARCHAR(20) PRIMARY KEY COMMENT '사용자ID',
`MEMBER_PW` VARCHAR(100) NOT NULL COMMENT '비밀번호',
`USERNAME` VARCHAR(50) NOT NULL COMMENT '사용자이름',
`TELNO` VARCHAR(14) COMMENT '연락처',
`EMAIL` VARCHAR(100) COMMENT '이메일',
`SEX` CHAR(1) COMMENT '성별코드',
`REMARK` VARCHAR(255) COMMENT '비고',
`USE_YN` varchar(1)  DEFAULT 'Y' COMMENT '사용여부',
`DELETE_YN` varchar(1)  DEFAULT 'N' COMMENT '삭제여부',
`WITHDRAW_YN` varchar(1)  DEFAULT 'N' COMMENT '탈퇴여부',
`FAILED_COUNT` int DEFAULT 0 COMMENT '로그인 실패 카운트',
`LOCK_YN` varchar(1)  DEFAULT 'N' COMMENT '계정잠금여부',
`CREATED_BY` varchar(255)  NULL COMMENT '생성자ID',
`CREATED_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
`MODIFIED_BY` varchar(255)  NULL COMMENT '수정자ID',
`MODIFIED_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시'
) COMMENT '사용자';

CREATE TABLE IF NOT EXISTS `T_COMMON_CODE` (
`CODE_GROUP` varchar(50) NOT NULL COMMENT '코드그룹',
`CODE` varchar(40) NOT NULL COMMENT '코드',
`CODE_NM` varchar(100)  NULL COMMENT '코드명',
`CODE_DESC` varchar(200)  NULL COMMENT '코드설명',
`SORT_ORD` int DEFAULT 0 COMMENT '정렬순서',
`DEFAULT_YN` varchar(1)  DEFAULT 'N' COMMENT '기본값여부',
`SUB_CODE_GROUP` varchar(40)  NULL COMMENT '서브코드그룹',
`SUB_CODE_GROUP_NM` varchar(255)  NULL COMMENT '서브코드그룹명',
`USE_YN` varchar(1)  DEFAULT 'Y' COMMENT '사용여부',
`DELETE_YN` varchar(1)  DEFAULT 'N' COMMENT '삭제여부',
`CREATED_BY` varchar(255)  NULL COMMENT '생성자ID',
`CREATED_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
`MODIFIED_BY` varchar(255)  NULL COMMENT '수정자ID',
`MODIFIED_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '수정일시',
PRIMARY KEY (`CODE_GROUP`,`CODE`)
) COMMENT='공통코드';

## mysql function
DELIMITER ;;
CREATE FUNCTION `FN_CODE_NM`(I_CODE_GROUP VARCHAR(50),I_CODE VARCHAR(40)) RETURNS varchar(100)
DETERMINISTIC
BEGIN
DECLARE V_CODE_NM VARCHAR(100) DEFAULT NULL;
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
RETURN '';
END;
	SELECT CODE_NM
	INTO V_CODE_NM
	FROM pdev.T_COMMON_CODE
	WHERE CODE_GROUP = I_CODE_GROUP
	AND CODE = I_CODE;
    RETURN IFNULL(V_CODE_NM,'');
END ;;
DELIMITER ;  

## ex
SELECT FN_CODE_NM('SEX','0');