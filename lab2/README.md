# LAB2 - Instrukcja uruchomienia

Instalacja dependencies: <br>
  - cd lab2 <br>
  - mvn install <br>

Plik csv znajduje się w folderze src/main/resources/data, aby poprawnie zaimportować pracowników z tego pliku należy 
użyć metody importFromCsv klasy ImportService i w argumencie podać ścieżkę relatywną: "data/file.csv" <br> Przykład:
```java
ImportSummary summaryCSV = ImportService.importFromCsv("data/file.csv");
System.out.println("Successfully imported from CSV: " + " " + String.valueOf(summaryCSV.getImportedSuccessfully()));
System.out.println("Errors in importing from CSV: " + "\n" + summaryCSV.getErrors());
```

Aby poprawnie zaimportować pracowników z API należy 
użyć metody fetchEmployeesFromAPI klasy APIService bez argumentu <br> Przykład:
```java
ImportSummary summaryAPI = ApiService.fetchEmployeesFromAPI();
System.out.println("Successfully imported from API: " + " " + String.valueOf(summaryAPI.getImportedSuccessfully()));
```


Obie metody importujące zwracają obiekt ImportSummary, który posiada w sobie metodę o nazwie: getImportedSuccessfully - jest to liczba całkowita poprawnie zaimportowanych pracowników z CSV, lub API.


  
