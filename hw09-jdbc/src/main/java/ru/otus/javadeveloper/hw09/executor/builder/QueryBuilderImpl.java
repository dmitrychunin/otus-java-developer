package ru.otus.javadeveloper.hw09.executor.builder;

import ru.otus.javadeveloper.hw09.executor.scanner.FieldContainer;
import ru.otus.javadeveloper.hw09.executor.scanner.FieldType;
import ru.otus.javadeveloper.hw09.executor.scanner.ScannerResult;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class QueryBuilderImpl implements QueryBuilder {
    private final static String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private final static String SELECT_TEMPLATE = "SELECT * FROM %s WHERE %s = ?";
    private final static String UPDATE_TEMPLATE = "UPDATE %s SET %s WHERE %s = ?";

    @Override
    public String buildInsert(ScannerResult scannerResult) {
        StringBuilder fieldNames = new StringBuilder();
        StringBuilder fieldValues = new StringBuilder();

        List<FieldContainer> notIdFieldContainers = getNotIdFields(scannerResult);
        for (FieldContainer container : notIdFieldContainers) {

            fieldNames.append(container.getName() + ",");
            Object value = container.getValue();
            if (String.class.equals(value.getClass())) {
                fieldValues.append("'" + value + "',");
            } else {
                fieldValues.append(value + ",");
            }
        }
        fieldNames.deleteCharAt(fieldNames.length() - 1);
        fieldValues.deleteCharAt(fieldValues.length() - 1);

        return format(INSERT_TEMPLATE, scannerResult.getClassName(), fieldNames.toString(), fieldValues.toString());
    }

    @Override
    public String buildSelectById(ScannerResult scannerResult) {
        return format(SELECT_TEMPLATE, scannerResult.getClassName(), scannerResult.getFirstIdFieldContainer().getName());
    }

    @Override
    public String buildUpdateById(ScannerResult scannerResult) {
        StringBuilder columnValueString = new StringBuilder();
        List<FieldContainer> notIdFieldContainers = getNotIdFields(scannerResult);
        for (FieldContainer container : notIdFieldContainers) {
            Object value = container.getValue();
            columnValueString
                    .append(container.getName())
                    .append("=");
            if (String.class.equals(value.getClass())) {
                columnValueString.append("'" + value + "',");
            } else {
                columnValueString.append(value + ",");
            }
        }
        columnValueString.deleteCharAt(columnValueString.length() - 1);
        return format(UPDATE_TEMPLATE, scannerResult.getClassName(), columnValueString.toString(), scannerResult.getFirstIdFieldContainer().getName());
    }

    private List<FieldContainer> getNotIdFields(ScannerResult scannerResult) {
        return scannerResult.getFieldContainers().stream().filter(fieldContainer -> fieldContainer.getFieldType() != FieldType.ID).collect(Collectors.toList());
    }
}
