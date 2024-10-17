import com.google.gson.JsonSyntaxException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class InterfazConversor extends JFrame {

    private JComboBox<String> opcionesConversion;
    private JTextArea resultadosArea;
    private JButton btnConvertir, btnSalir;
    private ConsultaConversion consulta;
    private Calculos calculos;
    private List<String> respuestas;
    private GeneradorDeArchivos generador;

    public InterfazConversor() {
        consulta = new ConsultaConversion();
        calculos = new Calculos(consulta);
        respuestas = new ArrayList<>();
        generador = new GeneradorDeArchivos();

        setTitle("Conversor de Monedas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Creando el menú de opciones
        String[] opciones = {
                "Peso Mexicano => Dólar Estadounidense",
                "Peso Mexicano => Euro",
                "Peso Mexicano => Libra Esterlina",
                "Dólar Estadounidense => Peso Mexicano",
                "Euro => Peso Mexicano",
                "Libra Esterlina => Peso Mexicano",
                "Otra opción de conversión"
        };
        opcionesConversion = new JComboBox<>(opciones);
        add(opcionesConversion, BorderLayout.NORTH);

        // Área de texto para mostrar resultados
        resultadosArea = new JTextArea(10, 30);
        resultadosArea.setEditable(false);
        add(new JScrollPane(resultadosArea), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnConvertir = new JButton("Convertir");
        btnSalir = new JButton("Salir");
        panelBotones.add(btnConvertir);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);

        // Agregar acciones a los botones
        btnConvertir.addActionListener(new ConvertirListener());
        btnSalir.addActionListener(e -> salirPrograma());

        setVisible(true);
    }

    private class ConvertirListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int opcionElegida = opcionesConversion.getSelectedIndex() + 1;

            // Obtener la marca de tiempo actual
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);

            try {
                switch (opcionElegida) {
                    case 1:
                        calculos.almacenarValores("MXN", "USD");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 2:
                        calculos.almacenarValores("MXN", "EUR");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 3:
                        calculos.almacenarValores("MXN", "GBP");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 4:
                        calculos.almacenarValores("USD", "MXN");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 5:
                        calculos.almacenarValores("EUR", "MXN");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 6:
                        calculos.almacenarValores("GBP", "MXN");
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    case 7:
                        calculos.almacenarValoresPersonalizados();
                        respuestas.add(formattedDate + " - " + calculos.obtenerMensajeRespuesta());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Ingrese una opción válida");
                }
                resultadosArea.append(respuestas.get(respuestas.size() - 1) + "\n");

            } catch (JsonSyntaxException | NullPointerException ex) {
                JOptionPane.showMessageDialog(null, "Error. Ingrese solo códigos de moneda válidos.");
            } catch (NumberFormatException | InputMismatchException ex) {
                JOptionPane.showMessageDialog(null, "Error. Ingrese un valor numérico válido.");
            }
        }
    }

    private void salirPrograma() {
        generador.guardarJson(respuestas);
        System.exit(0);
    }

    public static void main(String[] args) {
        new InterfazConversor();
    }
}
