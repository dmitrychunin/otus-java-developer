package ru.otus.javadeveloper.hw09.builder;

import ru.otus.javadeveloper.hw09.scanner.ClassScannerResult;
import ru.otus.javadeveloper.hw09.scanner.ObjectScannerResult;

import java.util.Map;

import static java.lang.String.format;

public class QueryBuilderImpl implements QueryBuilder {
    private final static String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private final static String SELECT_TEMPLATE = "SELECT * FROM %s WHERE %s = ?";
    private final static String UPDATE_TEMPLATE = "UPDATE %s SET %s WHERE %s = ?";

    @Override
    public String buildInsert(ObjectScannerResult objectScannerResult) {
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        Map<String, Object> fieldMap = objectScannerResult.getFieldMap();
        for (Map.Entry<String, Object> stringObjectEntry : fieldMap.entrySet()) {

            fieldNames.append(stringObjectEntry.getKey() + ",");
            Object value = stringObjectEntry.getValue();
            if (String.class.equals(value.getClass())) {
                fieldValues.append("'" + value + "',");
            } else {
                fieldValues.append(value + ",");
            }
        }
        fieldNames.deleteCharAt(fieldNames.length() - 1);
        fieldValues.deleteCharAt(fieldValues.length() - 1);

        return format(INSERT_TEMPLATE, objectScannerResult.getClassName(), fieldNames.toString(), fieldValues.toString());
    }

    @Override
    public String buildSelectById(ClassScannerResult classScannerResult) {
        return format(SELECT_TEMPLATE, classScannerResult.getClassName(), classScannerResult.getIdName());
    }

    @Override
    public String buildUpdateById(ObjectScannerResult objectScannerResult) {
        StringBuilder columnValueString = new StringBuilder();
        Map<String, Object> fieldMap = objectScannerResult.getFieldMap();
        for (Map.Entry<String, Object> stringObjectEntry : fieldMap.entrySet()) {
            Object value = stringObjectEntry.getValue();
            columnValueString
                    .append(stringObjectEntry.getKey())
                    .append("=");
            if (String.class.equals(value.getClass())) {
                columnValueString.append("'" + value + "',");
            } else {
                columnValueString.append(value + ",");
            }
        }
        columnValueString.deleteCharAt(columnValueString.length() - 1);
        return format(UPDATE_TEMPLATE, objectScannerResult.getClassName(), columnValueString.toString(), objectScannerResult.getIdName());
    }
}
