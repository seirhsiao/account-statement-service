package com.example.statementservice.service;

import com.example.statementservice.config.TaskCache;
import com.example.statementservice.model.TaskStatus;
import com.example.statementservice.model.Transaction;
import com.example.statementservice.util.RestClient;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatementServiceTest {
    @Mock
    private RestClient restClient;

    @Mock
    private TaskCache taskCache;

    @InjectMocks
    private StatementService statementService;

    String datePattern = "yyyy-MM-dd'T'HH:mm:ss";

    @Test
    void generateStatements_shouldStoreCompletedResultWhenSuccessful() throws ParseException {
        String taskId = UUID.randomUUID().toString();
        List<Transaction> mockTransactions = List.of(
                new Transaction("010000032", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                        "Fund transfer", "D", 100d, "Friends Name", "AED"),
                new Transaction("010000033", DateUtils.parseDate("2024-11-12T00:00:00", datePattern),
                        "Bill Payment", "D", 500d, "Friends Name", "AED")
        );
        String mockPdf = "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==";
        when(restClient.getAllTransactions(anyString(), anyString(), anyString())).thenReturn(mockTransactions);
        when(restClient.generatePdf(mockTransactions)).thenReturn(mockPdf);
        statementService.generateStatements(taskId, "2024-01-01", "2024-06-30", "1000000001");
        verify(taskCache).put(eq(taskId), argThat(
                result -> result.status() == TaskStatus.COMPLETED && result.pdfBase64().equals(mockPdf)));
    }

    @Test
    void generateStatements_shouldStoreFailedResultOnException() {
        String taskId = UUID.randomUUID().toString();
        when(restClient.getAllTransactions(anyString(), anyString(), anyString())).thenThrow(new RuntimeException("Mock error"));
        statementService.generateStatements(taskId, "2024-01-01", "2024-06-30", "1000000001");
        verify(taskCache).put(eq(taskId), argThat(result -> result.status() == TaskStatus.FAILED));
    }
}