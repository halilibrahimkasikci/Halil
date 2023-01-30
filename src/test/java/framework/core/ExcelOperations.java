//  package framework.core;
//
//
//  import org.apache.poi.ss.usermodel.*;
//  import org.junit.Assert;
//
//  import java.io.FileInputStream;
//  import java.io.IOException;
//
//
//  public class ExcelOperations {
//      FileInputStream inputStream;
//      Workbook workbook;
//      Sheet sheet;
//
//      public String getData(String filePath, int Sheet, int Row, int Col) {
//          getConnection(filePath, Sheet);
//          String Value = " ";
//          org.apache.poi.ss.usermodel.Row row = sheet.getRow(Row - 1);
//          Value += row.getCell(Col - 1);
//          Value = Value.trim();
//          if (Value.length() <= 0) {
//              Value = "EMPTY";
//              Assert.fail("No value found in the provided index");
//          }
//          closeSession();
//          return Value;
//      }
//
//      public String getDataSpecific(String filePath, int Sheet, int Row, int Col) {
//          getConnection(filePath, Sheet);
//          String Value = " ";
//          org.apache.poi.ss.usermodel.Row row = sheet.getRow(Row - 1);
//          Value += row.getCell(Col - 1);
//          Value = Value.trim();
//          closeSession();
//          return Value;
//      }
//
//      /**
//       * This method takes Sheet and Column number
//       *
//       * @param filePath
//       * @param Sheet
//       * @param Col
//       * @return "columnValue" The Sum Of The Column Values as a double !
//       * @Attention ! This operation can only be used for numerical operations.
//       * @author furkan.simsek
//       */
//      public double getSumOfTheColumnValues(String filePath, int Sheet, int Col) {
//          getConnection(filePath, Sheet);
//          int rowCount = sheet.getPhysicalNumberOfRows();
//          double columnValue = 0.0;
//          try {
//              for (int i = 1; i < rowCount - 1; i++) {
//                  Row row = sheet.getRow(i);
//                  double currentValue = row.getCell(Col - 1).getNumericCellValue();
//                  columnValue += currentValue;
//              }
//
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
//          closeSession();
//          return columnValue;
//      }
//
//      /**
//       * This method combines and returns all values in the index-given column.
//       *
//       * @param filePath
//       * @param Sheet
//       * @param Col
//       * @return "columnValue"
//       * @author furkan.simsek
//       */
//      public String readColumnValue(String filePath, int Sheet, int Col) {
//          getConnection(filePath, Sheet);
//          int rowCount = sheet.getPhysicalNumberOfRows();
//          String columnValue = " ";
//          for (int i = 1; i < rowCount; i++) {
//              Row row = sheet.getRow(i);
//              try {
//                  columnValue += row.getCell(Col - 1) + ",";
//              } catch (Exception e) {
//                  columnValue += "EMPTY,";
//                  Assert.fail("No value found in the provided index: \"" + Col + "\"");
//              }
//          }
//          columnValue = columnValue.trim();
//          closeSession();
//          return columnValue;
//      }
//      /**
//       * This Method combines and returns all values in the index-given row
//       *
//       * @param filePath
//       * @param Sheet
//       * @param Row
//       * @return "rowValue"
//       * @author furkan.simsek
//       */
//      public String readRowValue(String filePath, int Sheet, int Row) {
//          getConnection(filePath, Sheet);
//          String rowValue = " ";
//          Row row = sheet.getRow(Row - 1);
//          int cellCount = row.getPhysicalNumberOfCells();
//          for (int i = 0; i < cellCount; i++) {
//              if (row.getCell(i).toString().length() > 0) {
//                  rowValue += row.getCell(i) + ",";
//                  rowValue = rowValue.trim();
//              }
//          }
//          closeSession();
//          return rowValue;
//      }
//
//      /**
//       * This Method takes Column names and returns the index(+1) of searched column in all headers.
//       *
//       * @param filePath
//       * @param Sheet
//       * @param columnName
//       * @return "Col" Index number
//       * @author furkan.simsek
//       */
//      public int returnColumnIndex(String filePath, int Sheet, String columnName) {
//          getConnection(filePath, Sheet);
//          Row row = sheet.getRow(0);
//          int cellCount = row.getPhysicalNumberOfCells();
//          int Col = 0;
//          for (int i = 0; i < cellCount; i++) {
//              if (row.getCell(i).toString().equalsIgnoreCase(columnName)) {
//                  Col = i + 1;
//              }
//          }
//          if (Col == 0) {
//              Assert.fail("The searched name does not have a column header");
//          }
//          closeSession();
//          return Col;
//      }
//
//      /**
//       * This Method returns the index (row and column) of searched text text.
//       *
//       * @param filePath
//       * @param Sheet
//       * @param searchedText
//       * @return int[]{Row, Col};   arr[0] = Row arr[1] = Col
//       * @author furkan.simsek
//       */
//      public int[] searchData(String filePath, int Sheet, String searchedText) {
//          getConnection(filePath, Sheet);
//          int Col = 0;
//          int Row = 0;
//          int rowCount = sheet.getPhysicalNumberOfRows();
//          for (int i = 0; i < rowCount; i++) {
//              Row row = sheet.getRow(i);
//              int cellCount = row.getPhysicalNumberOfCells();
//              for (int j = 0; j < cellCount; j++) {
//                  if (row.getCell(j).toString().equals(searchedText)) {
//                      Col = j + 1;
//                      Row = i + 1;
//                  }
//              }
//          }
//          closeSession();
//          return new int[]{Row, Col};
//      }
//
//      /**
//       * This Method Updates a Excel file at specified row and column.
//       *
//       * @param filePath
//       * @param Sheet
//       * @param Row
//       * @param Col
//       * @param value
//       * @author furkan.simsek
//       */
//      public void updateCell(String filePath, int Sheet, int Row, int Col, String value) {
//          getConnection(filePath, Sheet);
//          Sheet sheet = workbook.getSheetAt(Sheet - 1);
//          try {
//              Cell cell2Update = sheet.getRow(Row - 1).getCell(Col - 1);
//              cell2Update.setCellValue(value);
//
//          } catch (Exception e) {
//              e.printStackTrace();
//              Assert.fail(e.toString());
//          }
//          closeSession();
//      }
//
//      void getConnection(String filePath, int Sheet) {
//          String userprofile = System.getenv("USERPROFILE");
//          filePath = filePath.replace("%USERPROFILE%", userprofile);
//
//          if (Sheet < 1) {
//              Sheet = 1;
//          }
//          workbook = null;
//          inputStream = null;
//          try {
//              inputStream = new FileInputStream(filePath);
//              workbook = WorkbookFactory.create(inputStream);
//          } catch (Exception e) {
//              e.printStackTrace();
//          }
//          assert workbook != null;
//          sheet = workbook.getSheetAt(Sheet - 1);
//
//      }
//
//      void closeSession() {
//          try {
//              inputStream.close();
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//          try {
//              workbook.close();
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//      }
//  }
//