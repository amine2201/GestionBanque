package presentation.vue.generalVue;

import presentation.modele.util.Log;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableLog extends AbstractTableModel {
    Object[][] data;
    String[] columnNames;

    private void initColumnNames(String... names){
        columnNames=new String[names.length];
        System.arraycopy(names, 0, columnNames, 0, names.length);

    }
    public void initLogsData(List<Log> logs){
        data = new Object[logs.size()][columnNames.length];

        int i=0;
        for(Log log : logs){
            data[i][0]=log.getDate();
            data[i][1]=log.getTime();
            data[i][2]=log.getType()+log.getMessage();
            i++;
        }
        this.fireTableDataChanged();
    }
    public TableLog(String... names){
        initColumnNames(names);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
