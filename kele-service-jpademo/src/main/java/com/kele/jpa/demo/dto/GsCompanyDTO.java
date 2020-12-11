package com.kele.jpa.demo.dto;

import com.kele.jpa.demo.entity.GsCompanyEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class GsCompanyDTO {

    private String gsName;

    private String taxCode;

    private BigDecimal registerMoney;

    private Set<GsEmployeeDTO> employeeDTOSet = new HashSet<>();

    public static GsCompanyDTO convertFromEntity(GsCompanyEntity entity) {

        GsCompanyDTO dto = new GsCompanyDTO();
        BeanUtils.copyProperties(entity, dto);
        Set<GsEmployeeDTO> collect = entity.getEmployeeEntitySet().stream().map(e -> {
            GsEmployeeDTO employeeDTO = new GsEmployeeDTO();
            BeanUtils.copyProperties(e, employeeDTO);
            return employeeDTO;
        }).collect(Collectors.toSet());
        dto.setEmployeeDTOSet(collect);

        return dto;

    }

}
