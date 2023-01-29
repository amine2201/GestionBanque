package presentation.vue.compteVue;

import presentation.modele.entitesDeLaBanque.Compte;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableCompte extends AbstractTableModel {
    Object[][] data;
    String[] columnNames;

    private void initColumnNames(String... names){
        columnNames=new String[names.length];
        System.arraycopy(names, 0, columnNames, 0, names.length);

    }
    public void initComptesData(List<Compte> comptes){
        data = new Object[comptes.size()][columnNames.length];

        int i=0;
        for(Compte compte : comptes){
//            id,dateCreation,solde,idClient
            data[i][0]=compte.getNumeroCompte();
            data[i][1]=compte.getDateCreation().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            data[i][2]=compte.getSolde();
            data[i][3]=compte.getPropriétaire().getNomComplet();
            i++;
        }
        this.fireTableDataChanged();
    }
    public TableCompte(String... names){
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
