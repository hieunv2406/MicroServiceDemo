select e.employee_id employeeId,
       e.code        code,
       e.username    username,
       e.full_name   fullName,
       e.email       email,
       e.birthday    birthday,
       e.gender      gender,
       e.address
from employee e
where 1 = 1