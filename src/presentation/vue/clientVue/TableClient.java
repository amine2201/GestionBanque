package presentation.vue.clientVue;

import presentation.modele.entitesDeLaBanque.Client;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableClient extends AbstractTableModel {
    Object[][] data;
    String[] columnNames;

    private void initColumnNames(String... names){
        columnNames=new String[names.length];
        System.arraycopy(names, 0, columnNames, 0, names.length);

    }
    public void initClientsData(List<Client> clients){
        data = new Object[clients.size()][columnNames.length];

        int i=0;
        for(Client client : clients){
            data[i][0]=client.getId();
            data[i][1]=client.getNom();
            data[i][2]=client.getPrenom();
            data[i][3]=client.getLogin();
            data[i][4]=client.getMotDePasse();
            data[i][5]=client.getCin();
            data[i][6]=client.getEmail();
            data[i][7]=client.getTel();
            data[i][8]=client.getSexe();

            i++;
        }
        this.fireTableDataChanged();
    }
    public TableClient(String... names){
        initColumnNames(names);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (data[0].length==0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
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
