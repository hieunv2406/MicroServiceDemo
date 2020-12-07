package com.example.admin.repository;

import com.example.admin.common.Constant;
import com.example.admin.common.dto.BaseDTO;
import com.example.admin.common.dto.Datatable;
import com.example.admin.common.dto.ResultInsideDTO;
import com.example.admin.common.repository.BaseRepository;
import com.example.admin.common.utils.DataUtil;
import com.example.admin.data.dto.EmployeeDTO;
import com.example.admin.data.entity.EmployeeEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class EmployeeRepositoryImpl extends BaseRepository implements EmployeeRepository {


    @Override
    public EmployeeDTO findEmployeeById(Long employee_id) {
        EmployeeEntity employeeEntity = getEntityManager().find(EmployeeEntity.class, employee_id);
        EmployeeDTO employeeDTO = employeeEntity.toDto();
        return employeeDTO;
    }

    @Override
    public ResultInsideDTO insertEmployee(EmployeeDTO employeeDTO) {
        ResultInsideDTO resultInsideDTO = new ResultInsideDTO();
        resultInsideDTO.setKey(Constant.RESPONSE_KEY.SUCCESS);
        EmployeeEntity employeeEntity = getEntityManager().merge(employeeDTO.toEntity());
        resultInsideDTO.setId(employeeEntity.getEmployeeId());
        return resultInsideDTO;
    }

    @Override
    public ResultInsideDTO updateEmployee(EmployeeDTO employeeDTO) {
        ResultInsideDTO resultInsideDTO = new ResultInsideDTO();
        resultInsideDTO.setKey(Constant.RESPONSE_KEY.SUCCESS);
        EmployeeEntity employeeEntity = getEntityManager().find(EmployeeEntity.class, employeeDTO.getEmployeeId());
        if (employeeEntity != null) {
            employeeEntity = getEntityManager().merge(employeeDTO.toEntity());
            resultInsideDTO.setId(employeeEntity.getEmployeeId());
        } else {
            resultInsideDTO.setKey(Constant.RESPONSE_KEY.RECORD_NOT_EXIST);
        }
        return resultInsideDTO;
    }

    @Override
    public ResultInsideDTO deleteEmployeeById(Long employeeId) {
        ResultInsideDTO resultInsideDTO = new ResultInsideDTO();
        resultInsideDTO.setKey(Constant.RESPONSE_KEY.SUCCESS);
        EmployeeEntity employeeEntity = getEntityManager().find(EmployeeEntity.class, employeeId);
        getEntityManager().remove(employeeEntity);
        return resultInsideDTO;
    }

    @Override
    public Datatable getListEmployeeDTO(EmployeeDTO employeeDTO) {
        BaseDTO baseDTO = sqlSearch(employeeDTO);
        return getListDataTableBySqlQuery(baseDTO.getSqlQuery(),
                baseDTO.getParameters(), employeeDTO.getPage(), employeeDTO.getPageSize(),
                EmployeeDTO.class,
                employeeDTO.getSortName(), employeeDTO.getSortType());
    }

    @Override
    public List<EmployeeDTO> getListDataExport(EmployeeDTO employeeDTO) {
        BaseDTO baseDTO = sqlSearch(employeeDTO);
        return getNamedParameterJdbcTemplate().query(baseDTO.getSqlQuery()
                , baseDTO.getParameters()
                , BeanPropertyRowMapper.newInstance(EmployeeDTO.class));
    }

    @Override
    public List<Map<String, Object>> getListEmployeeMap() {

        Map<String, Object> beanMap = new HashMap<>();
        String sql = " select " +
                " t.cot1 cot1, " +
                " t.cot2 cot2, " +
                " t.cot3 cot3 " +
                " from " +
                " tabledetest t " +
                " where " +
                " 1 = 1 ";
        List<Map<String, Object>> mapResult = getNamedParameterJdbcTemplate().queryForList(sql, beanMap);
        return mapResult;
    }

    private BaseDTO sqlSearch(EmployeeDTO employeeDTO) {
        BaseDTO baseDTO = new BaseDTO();
        Map<String, Object> parameter = new HashMap<>();
        String sql = getSQLFromFile("employee", "getEmployeeDTO");
        if (employeeDTO != null) {
            if (!DataUtil.isNullOrEmpty(employeeDTO.getCode())) {
                sql += " And e.code = :code ";
                parameter.put("code", employeeDTO.getCode());
            }
            if (!DataUtil.isNullOrEmpty(employeeDTO.getUsername())) {
                sql += " And lower(e.username) Like lower(:username) ";
                parameter.put("username", DataUtil.convertSqlLike(employeeDTO.getUsername()));
            }
            if (!DataUtil.isNullOrEmpty(employeeDTO.getGender())) {
                sql += " And e.gender = :gender ";
                parameter.put("gender", employeeDTO.getGender());
            }
        }
        sql += " ORDER BY e.employee_id ASC ";
        baseDTO.setSqlQuery(sql);
        baseDTO.setParameters(parameter);
        return baseDTO;
    }
}
