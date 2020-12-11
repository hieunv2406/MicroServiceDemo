package com.example.admin.business;

import com.example.admin.common.CommonExport;
import com.example.admin.common.I18n;
import com.example.admin.common.config.CellConfigExport;
import com.example.admin.common.config.ConfigFileExport;
import com.example.admin.common.config.ConfigHeaderExport;
import com.example.admin.common.dto.Datatable;
import com.example.admin.common.dto.ResultInsideDTO;
import com.example.admin.common.utils.DataUtil;
import com.example.admin.data.dto.EmployeeDTO;
import com.example.admin.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Slf4j
@Service
public class EmployeeBusinessImpl implements EmployeeBusiness {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO findEmployeeById(Long employeeId) {
        log.info("findEmployeeById", employeeId);
        return employeeRepository.findEmployeeById(employeeId);
    }

    @Override
    public ResultInsideDTO insertEmployee(EmployeeDTO employeeDTO) {
        log.info("insertEmployee", employeeDTO);
        return employeeRepository.insertEmployee(employeeDTO);
    }

    @Override
    public ResultInsideDTO updateEmployee(EmployeeDTO employeeDTO) {
        log.info("updateEmployee", employeeDTO);
        return employeeRepository.updateEmployee(employeeDTO);
    }

    @Override
    public ResultInsideDTO deleteEmployeeById(Long employeeId) {
        log.info("deleteEmployeeById", employeeId);
        return employeeRepository.deleteEmployeeById(employeeId);
    }

    @Override
    public Datatable getListEmployeeDTO(EmployeeDTO employeeDTO) {
        log.info("getListEmployeeDTO", employeeDTO);
        return employeeRepository.getListEmployeeDTO(employeeDTO);
    }

    @Override
    public List<Map<String, Object>> getListEmployeeMap() {
        log.info("getListEmployeeMap");
        return employeeRepository.getListEmployeeMap();
    }

    @Override
    public List<EmployeeDTO> getListDataExport(EmployeeDTO employeeDTO) {
        log.info("getListDataExport", employeeDTO);
        return employeeRepository.getListDataExport(employeeDTO);
    }

    @Override
    public File exportData(EmployeeDTO employeeDTO) throws Exception {
        List<EmployeeDTO> employeeDTOList = employeeRepository.getListDataExport(employeeDTO);
        for (EmployeeDTO dto : employeeDTOList) {
            if (!DataUtil.isNullOrEmpty(dto.getGender())) {
                if ("0".equals(String.valueOf(dto.getGender()))) {
                    dto.setGenderStr(I18n.getLanguage("language.employee.gender.0"));
                } else {
                    dto.setGenderStr(I18n.getLanguage("language.employee.gender.1"));
                }
            } else {
                dto.setGenderStr("N/A");
            }
        }
        return exportTemplate(employeeDTOList, "EXPORT");
    }

    private File exportTemplate(List<EmployeeDTO> dtoList, String key) throws Exception {
        String fileNameOut;
        String subTitle = null;
        String sheetName = I18n.getLanguage("language.employee.title");
        String title = I18n.getLanguage("language.employee.title");
        List<ConfigFileExport> fileExportList = new ArrayList<>();
        List<ConfigHeaderExport> headerExportList;
        if ("RESULT_IMPORT".equalsIgnoreCase(key)) {
            headerExportList = readerHeaderSheet("code"
                    , "username"
                    , "fullName"
                    , "email"
                    , "birthday"
                    , "genderStr"
                    , "address");
            fileNameOut = "EMPLOYEE_RESULT_IMPORT";
        } else {
            headerExportList = readerHeaderSheet("code"
                    , "username"
                    , "fullName"
                    , "email"
                    , "birthday"
                    , "genderStr"
                    , "address");
            fileNameOut = "EMPLOYEE_EXPORT";
            subTitle = String.valueOf(new Date());
        }
        Map<String, String> fieldSplit = new HashMap<>();
        ConfigFileExport configFileExport = new ConfigFileExport(
                dtoList,
                sheetName,
                title,
                subTitle,
                7,
                3,
                9,
                true,
                "language.employee",
                headerExportList,
                fieldSplit,
                "",
                I18n.getLanguage("language.common.firstLeftHeaderTitle"),
                I18n.getLanguage("language.common.secondLeftHeaderTitle"),
                I18n.getLanguage("language.common.firstRightHeaderTitle"),
                I18n.getLanguage("language.common.secondRightHeaderTitle"));
        configFileExport.setLangKey("i18n/vi");
        List<CellConfigExport> lstCellSheet = new ArrayList<>();
        CellConfigExport cellSheet;
        cellSheet = new CellConfigExport(7,
                0,
                0,
                "OK",
                "HEAD",
                "STRING");
        lstCellSheet.add(cellSheet);
        configFileExport.setLstCreateCell(lstCellSheet);
        fileExportList.add(configFileExport);
        //cấu hình đường dẫn
        String fileTemplate = "template" + File.separator + "TEMPLATE_EXPORT.xlsx";
        String rootPath = "tempFolder" + File.separator;
        File fileExport = CommonExport.exportExcel(
                fileTemplate,
                fileNameOut,
                fileExportList,
                rootPath,
                new String[]{}
        );
        return fileExport;
    }

    private List<ConfigHeaderExport> readerHeaderSheet(String... col) {
        List<ConfigHeaderExport> configHeaderExports = new ArrayList<>();
        for (int i = 0; i < col.length; i++) {
            configHeaderExports.add((new ConfigHeaderExport(col[i]
                    , "LEFT"
                    , false
                    , 0
                    , 0
                    , new String[]{}
                    , new String[]{}
                    , "STRING")));
        }
        return configHeaderExports;
    }

}
