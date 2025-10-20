package service;

import model.Employee;
import model.ImportSummary;
import model.Position;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApiServiceTest {

    @Test
    void testFetchEmployeesFromAPI_Valid_AmountOfImported() throws Exception {
        ImportSummary mockSummary = mock(ImportSummary.class);
        when(mockSummary.getImportedSuccessfully()).thenReturn(1);


        try (MockedStatic<ApiService> mockedStatic = mockStatic(ApiService.class)) {
            mockedStatic.when(ApiService::fetchEmployeesFromAPI).thenReturn(mockSummary);


            int result = ApiService.fetchEmployeesFromAPI().getImportedSuccessfully();

            // Assert
            assertEquals(1, result);
        }
    }

}
