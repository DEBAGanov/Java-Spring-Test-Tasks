package com.example.uploadingfiles.storage;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SortingService {

    public void sortDataInFile(String filePath) {
        try {
            // Чтение данных из файла
            List<String> lines = readLinesFromFile(filePath);

            // Сортировка данных
            Collections.sort(lines);

            // Запись отсортированных данных обратно в файл
            writeLinesToFile(filePath, lines);

            System.out.println("Данные в файле успешно отсортированы.");
        } catch (IOException e) {
            System.err.println("Ошибка при сортировке данных в файле: " + e.getMessage());
        }
    }

    private List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private void writeLinesToFile(String filePath, List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }
}