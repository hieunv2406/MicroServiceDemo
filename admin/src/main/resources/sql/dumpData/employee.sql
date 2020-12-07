CREATE TABLE mydatabase.employee (
	employee_id BIGINT(20) auto_increment NOT NULL COMMENT 'id nhân viên',
	username varchar(50) NULL COMMENT 'username',
	code varchar(50) NULL COMMENT 'mã nhân viên',
	full_name varchar(255) NULL COMMENT 'tên nhân viên',
	email varchar(100) NULL COMMENT 'email',
	gender INT NULL COMMENT 'giới tính',
	birthday DATE NULL COMMENT 'ngành sinh',
	address varchar(255) NULL COMMENT 'địa chỉ',
	CONSTRAINT employee_pk PRIMARY KEY (employee_id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_vietnamese_ci
COMMENT='bảng thông tin nhân viên';
