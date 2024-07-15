package com.green.project2nd.budget;

import com.green.project2nd.common.CheckMapper;
import com.green.project2nd.common.model.CustomFileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(BudgetService.class)
class BudgetServiceTest {

    @Value("${file.directory}") String uploadPath;
    @MockBean BudgetMapper mapper;
    @MockBean CustomFileUtils customFileUtils;
    @MockBean CheckMapper checkMapper;

    @Test
    void postBudget() {
    }

    @Test
    void patchBudget() {
    }

    @Test
    void getBudget() {
    }

    @Test
    void getBudgetPic() {
    }

    @Test
    void deleteBudget() {
    }

    @Test
    void getBudgetMember() {
    }

    @Test
    void getBudgetMonthly() {
    }
}