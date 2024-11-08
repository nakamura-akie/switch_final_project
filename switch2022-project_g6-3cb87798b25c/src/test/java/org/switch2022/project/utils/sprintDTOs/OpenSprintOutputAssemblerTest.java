package org.switch2022.project.utils.sprintDTOs;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.utils.assembler.OpenSprintOutputAssembler;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OpenSprintOutputAssemblerTest {

    @Test
    void generateDTO_generatesDTOcorrectly_True() {
        Sprint sprintDouble = mock(Sprint.class);

        when(sprintDouble.getSprintCode()).thenReturn(new SprintCode("S1"));
        when(sprintDouble.getSprintStatus()).thenReturn(SprintStatus.OPENED);
        when(sprintDouble.getStartDate()).thenReturn(LocalDate.of(2024,03,02));
        when(sprintDouble.getEndDate()).thenReturn(LocalDate.of(2024,05,03));

        OpenSprintOutputDTO outputDTO = new OpenSprintOutputDTO();
        outputDTO.sprintCode = "S1";
        outputDTO.startDate = "2024-03-02";
        outputDTO.endDate = "2024-05-03";
        outputDTO.sprintStatus = "OPENED";


        OpenSprintOutputDTO result = OpenSprintOutputAssembler.generateDTO(sprintDouble);

        assertEquals(outputDTO, result);
    }

    @Test
    void sprintList_generatesSprintListCorrectly_True() {
        OpenSprintOutputDTO outputDTO = new OpenSprintOutputDTO();
        outputDTO.sprintCode = "S1";
        outputDTO.startDate = "2024-03-02";
        outputDTO.endDate = "2024-05-03";
        outputDTO.sprintStatus = "OPENED";

        List<OpenSprintOutputDTO> expected = new ArrayList<>();
        expected.add(outputDTO);

        Sprint sprintDouble = mock(Sprint.class);

        when(sprintDouble.getSprintCode()).thenReturn(new SprintCode("S1"));
        when(sprintDouble.getSprintStatus()).thenReturn(SprintStatus.OPENED);
        when(sprintDouble.getStartDate()).thenReturn(LocalDate.of(2024,03,02));
        when(sprintDouble.getEndDate()).thenReturn(LocalDate.of(2024,05,03));

        List<Sprint> sprintList = new ArrayList<>();
        sprintList.add(sprintDouble);

        List<OpenSprintOutputDTO> result = OpenSprintOutputAssembler.createSprintList(sprintList.iterator());

        assertEquals(expected, result);
    }
}