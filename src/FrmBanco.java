import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import modelos.TipoCuenta;
import modelos.TipoTransaccion;
import servicios.CuentaServicio;
import servicios.TransaccionServicio;

public class FrmBanco extends JFrame {

    private JTable tblCuentas, tblTransacciones;
    private JPanel pnlEditarCuenta, pnlEditarTransaccion;
    private JLabel lblValor, lblTasa, lblPlazo;
    private JTextField txtNumero, txtTitular, txtTasa, txtValor, txtValorTransaccion, txtPlazo;
    private JComboBox cmbTipoCuenta, cmbTipoTransaccion, cmbCuenta;

    JTabbedPane tp;

    public FrmBanco() {
        setSize(600, 400);
        setTitle("Cuentas Bancarias");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tbBanco = new JToolBar();

        JButton btnAgregarCuenta = new JButton();
        btnAgregarCuenta.setIcon(new ImageIcon(getClass().getResource("/iconos/AgregarCuenta.png")));
        btnAgregarCuenta.setToolTipText("Agregar Cuenta");
        btnAgregarCuenta.addActionListener(evt -> {
            btnAgregarCuentaClick();
        });
        tbBanco.add(btnAgregarCuenta);

        JButton btnQuitarCuenta = new JButton();
        btnQuitarCuenta.setIcon(new ImageIcon(getClass().getResource("/iconos/QuitarCuenta.png")));
        btnQuitarCuenta.setToolTipText("Quitar Cuenta");
        btnQuitarCuenta.addActionListener(evt -> {
            btnQuitarCuentaClick();
        });
        tbBanco.add(btnQuitarCuenta);

        JButton btnTransaccion = new JButton();
        btnTransaccion.setIcon(new ImageIcon(getClass().getResource("/iconos/Transaccion.png")));
        btnTransaccion.setToolTipText("Realizar Transacción");
        btnTransaccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnTransaccionClick();
            }
        });
        tbBanco.add(btnTransaccion);

        // Contenedor principal de CUENTAS con BoxLayout (vertical)
        JPanel pnlCuentas = new JPanel();
        pnlCuentas.setLayout(new BoxLayout(pnlCuentas, BoxLayout.Y_AXIS));

        // Panel 1 (oculto por defecto)
        pnlEditarCuenta = new JPanel();
        pnlEditarCuenta.setPreferredSize(new Dimension(pnlEditarCuenta.getWidth(), 100)); // Altura fija de 100px
        pnlEditarCuenta.setLayout(null);

        JLabel lblNumero = new JLabel("Número");
        lblNumero.setBounds(10, 10, 100, 25);
        pnlEditarCuenta.add(lblNumero);

        txtNumero = new JTextField();
        txtNumero.setBounds(110, 10, 100, 25);
        pnlEditarCuenta.add(txtNumero);

        JLabel lblTitular = new JLabel("Titular");
        lblTitular.setBounds(10, 40, 100, 25);
        pnlEditarCuenta.add(lblTitular);

        txtTitular = new JTextField();
        txtTitular.setBounds(110, 40, 100, 25);
        pnlEditarCuenta.add(txtTitular);

        lblTasa = new JLabel("Tasa de Interés");
        lblTasa.setBounds(10, 70, 100, 25);
        pnlEditarCuenta.add(lblTasa);

        txtTasa = new JTextField();
        txtTasa.setBounds(110, 70, 100, 25);
        pnlEditarCuenta.add(txtTasa);

        cmbTipoCuenta = new JComboBox();
        cmbTipoCuenta.setBounds(220, 10, 100, 25);

        DefaultComboBoxModel mdlTipoCuenta = new DefaultComboBoxModel(TipoCuenta.values());
        cmbTipoCuenta.setModel(mdlTipoCuenta);
        pnlEditarCuenta.add(cmbTipoCuenta);

        cmbTipoCuenta.addActionListener(e -> {
            switch ((TipoCuenta) cmbTipoCuenta.getSelectedItem()) {
                case AHORROS:
                    lblValor.setVisible(false);
                    txtValor.setVisible(false);
                    lblTasa.setVisible(true);
                    txtTasa.setVisible(true);
                    lblPlazo.setVisible(false);
                    txtPlazo.setVisible(false);
                    break;
                case CORRIENTE:
                    lblValor.setVisible(true);
                    lblValor.setText("Sobregiro:");
                    txtValor.setVisible(true);
                    lblTasa.setVisible(false);
                    txtTasa.setVisible(false);
                    lblPlazo.setVisible(false);
                    txtPlazo.setVisible(false);
                    break;
                case CREDITO:
                    lblValor.setVisible(true);
                    lblValor.setText("Valor Prestado:");
                    txtValor.setVisible(true);
                    lblTasa.setVisible(true);
                    txtTasa.setVisible(true);
                    lblPlazo.setVisible(true);
                    txtPlazo.setVisible(true);
                    break;
            }
        });

        lblValor = new JLabel("Valor");
        lblValor.setBounds(220, 40, 100, 25);
        lblValor.setVisible(false);
        pnlEditarCuenta.add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(320, 40, 100, 25);
        txtValor.setVisible(false);
        pnlEditarCuenta.add(txtValor);

        lblPlazo = new JLabel("Plazo");
        lblPlazo.setBounds(430, 40, 100, 25);
        lblPlazo.setVisible(false);
        pnlEditarCuenta.add(lblPlazo);

        txtPlazo = new JTextField();
        txtPlazo.setBounds(480, 40, 100, 25);
        txtPlazo.setVisible(false);
        pnlEditarCuenta.add(txtPlazo);

        JButton btnGuardarCuenta = new JButton("Guardar");
        btnGuardarCuenta.setBounds(220, 70, 100, 25);
        btnGuardarCuenta.addActionListener(evt -> {
            btnGuardarCuentaClick();
        });
        pnlEditarCuenta.add(btnGuardarCuenta);

        JButton btnCancelarCuenta = new JButton("Cancelar");
        btnCancelarCuenta.setBounds(320, 70, 100, 25);
        btnCancelarCuenta.addActionListener(evt -> {
            btnCancelarCuentaClick();
        });
        pnlEditarCuenta.add(btnCancelarCuenta);

        pnlEditarCuenta.setVisible(false); // Se oculta al inicio

        // Panel 2 (siempre visible)
        tblCuentas = new JTable();
        JScrollPane spListaCuentas = new JScrollPane(tblCuentas);

        CuentaServicio.mostrar(tblCuentas);

        // Agregar componentes
        pnlCuentas.add(pnlEditarCuenta);
        pnlCuentas.add(spListaCuentas);

        // JScrollPane para permitir desplazamiento si es necesario
        JScrollPane spCuentas = new JScrollPane(pnlCuentas);
        spCuentas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Contenedor principal de TRANSACCIONES con BoxLayout (vertical)
        JPanel pnlTransacciones = new JPanel();
        pnlTransacciones.setLayout(new BoxLayout(pnlTransacciones, BoxLayout.Y_AXIS));

        // Panel 1 (oculto por defecto)
        pnlEditarTransaccion = new JPanel();
        pnlEditarTransaccion.setPreferredSize(new Dimension(pnlEditarTransaccion.getWidth(), 100)); // Altura fija de
                                                                                                    // 100px
        pnlEditarTransaccion.setLayout(null);

        JLabel lblCuenta = new JLabel("Cuenta");
        lblCuenta.setBounds(10, 10, 100, 25);
        pnlEditarTransaccion.add(lblCuenta);

        cmbCuenta = new JComboBox();
        cmbCuenta.setBounds(110, 10, 400, 25);
        pnlEditarTransaccion.add(cmbCuenta);

        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setBounds(10, 40, 100, 25);
        pnlEditarTransaccion.add(lblTipo);

        cmbTipoTransaccion = new JComboBox();
        cmbTipoTransaccion.setBounds(110, 40, 100, 25);
        DefaultComboBoxModel mdlTipoTransaccion = new DefaultComboBoxModel(TipoTransaccion.values());
        cmbTipoTransaccion.setModel(mdlTipoTransaccion);
        pnlEditarTransaccion.add(cmbTipoTransaccion);

        JLabel lblValorTransaccion = new JLabel("Valor");
        lblValorTransaccion.setBounds(10, 70, 100, 25);
        pnlEditarTransaccion.add(lblValorTransaccion);

        txtValorTransaccion = new JTextField();
        txtValorTransaccion.setBounds(110, 70, 100, 25);
        pnlEditarTransaccion.add(txtValorTransaccion);

        JButton btnGuardarTransaccion = new JButton("Guardar");
        btnGuardarTransaccion.setBounds(220, 70, 100, 25);
        btnGuardarTransaccion.addActionListener(evt -> {
            btnGuardarTransaccionClick();
        });
        pnlEditarTransaccion.add(btnGuardarTransaccion);

        JButton btnCancelarTransaccion = new JButton("Cancelar");
        btnCancelarTransaccion.setBounds(320, 70, 100, 25);
        btnCancelarTransaccion.addActionListener(evt -> {
            btnCancelarTransaccionClick();
        });
        pnlEditarTransaccion.add(btnCancelarTransaccion);

        pnlEditarTransaccion.setVisible(false); // Se oculta al inicio

        // Panel 2 (siempre visible)
        tblTransacciones = new JTable();
        JScrollPane spListaTransacciones = new JScrollPane(tblTransacciones);

        TransaccionServicio.mostrar(tblTransacciones);

        // Agregar componentes
        pnlTransacciones.add(pnlEditarTransaccion);
        pnlTransacciones.add(spListaTransacciones);

        // JScrollPane para permitir desplazamiento si es necesario
        JScrollPane spTransacciones = new JScrollPane(pnlTransacciones);
        spTransacciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        tp = new JTabbedPane();
        tp.addTab("Cuentas", spCuentas);
        tp.addTab("Transacciones", spTransacciones);

        add(tbBanco, BorderLayout.NORTH);
        add(tp, BorderLayout.CENTER);
    }

    private void btnAgregarCuentaClick() {
        pnlEditarCuenta.setVisible(true);
        tp.setSelectedIndex(0);

    }

    private void btnQuitarCuentaClick() {
        if (tblCuentas.getSelectedRow() >= 0) {
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar la cuenta?", "",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                CuentaServicio.eliminar(tblCuentas.getSelectedRow());
                CuentaServicio.mostrar(tblCuentas);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una cuenta");
        }

    }

    private void btnGuardarCuentaClick() {
        TipoCuenta tipo = (TipoCuenta) cmbTipoCuenta.getSelectedItem();
        var cuentaAgregada = CuentaServicio.agregar(tipo,
                txtTitular.getText(),
                txtNumero.getText(),
                tipo == TipoCuenta.CORRIENTE ? Double.parseDouble(txtValor.getText()) : 0,
                tipo == TipoCuenta.AHORROS || tipo == TipoCuenta.CREDITO ? Double.parseDouble(txtTasa.getText()) : 0,
                tipo == TipoCuenta.CREDITO ? Double.parseDouble(txtValor.getText()) : 0,
                tipo == TipoCuenta.CREDITO ? Integer.parseInt(txtPlazo.getText()) : 0);
        CuentaServicio.mostrar(tblCuentas);
        cmbCuenta.addItem(cuentaAgregada.toString());
        pnlEditarCuenta.setVisible(false);

    }

    private void btnCancelarCuentaClick() {
        pnlEditarCuenta.setVisible(false);

    }

    private void btnTransaccionClick() {
        pnlEditarTransaccion.setVisible(true);
        tp.setSelectedIndex(1);

    }

    private void btnGuardarTransaccionClick() {
        double valor = Double.parseDouble(txtValorTransaccion.getText());
        if (valor > 0 && cmbCuenta.getSelectedIndex() >= 0 && cmbTipoTransaccion.getSelectedIndex() >= 0){
            var transaccionAgregada = TransaccionServicio.agregar(
                    CuentaServicio.getCuenta(cmbCuenta.getSelectedIndex()),
                    (TipoTransaccion) cmbTipoTransaccion.getSelectedItem(),
                    valor);
            if (transaccionAgregada != null) {
                TransaccionServicio.mostrar(tblTransacciones);
                pnlEditarTransaccion.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "No se pudo realizar la transacción");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Debe ingresar un valor positivo y seleccionar una cuenta");
        }

    }

    private void btnCancelarTransaccionClick() {
        pnlEditarTransaccion.setVisible(false);

    }

}
