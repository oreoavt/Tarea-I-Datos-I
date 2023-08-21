
/**
 * Instituto tecnológico de Costa Rica
 * Curso: ALgoritmos y Estructura de Datos
 * Trabajo extraclase #1 - Implementación de chat
 * Estudiantes: Bryan Monge y Emmanuel Calvo
 * Grupo #1
 * Profesor: Leonardo Araya
 * Ingeniería en Computadores
 * Periodo II Semestre 2023
 */

/**
 * Importación de librerías de diseño y comunicación vía Socket
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;

/**
 * Definición de la clase que contiene la integración del diseño de la interfaz,
 * además del comportamiento de los dos botones disponibles en la interfaz,
 * y el empaquetamiento del mensaje para ser mostrado a través del servidor
 * hacia la computadora receptora
 */
public class InterfaceCliente extends JFrame implements ActionListener, Runnable {

    // Definición de las variables contenidas en la interfaz
    private JTextArea textArea;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextArea chat_cliente;
    private JLabel nickname;
    private JTextField ip_user;
    JButton boton1;
    JButton boton2;

    /**
     * Este método se encarga de aceptar conexiones de clientes y recibir paquetes
     * de mensajes.
     * Los mensajes recibidos se muestran en un componente de chat.
     * 
     * @throws IOException Si ocurre un error de entrada/salida al comunicarse con
     *                     los clientes.
     */
    public void run() {
        try {
            ServerSocket servidor_cliente = new ServerSocket(9090);
            Socket cliente;
            Paquetería paqueteRecibido;
            while (true) {
                cliente = servidor_cliente.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
                paqueteRecibido = (Paquetería) flujoEntrada.readObject();
                chat_cliente.append("\n" + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Constructor de la clase InterfaceCliente.
     * Configuración del diseño de la interfaz
     */
    public InterfaceCliente() {
        setLayout(null);

        // Instrucción que el usuario debe realizar #1
        label3 = new JLabel("Username:");
        label3.setBounds(60, 40, 200, 30);
        add(label3);

        /**
         * Gestionar y mostrar el apodo de un usuario en una interfaz gráfica.
         * Permite al usuario ingresar su apodo y muestra dicho apodo en la etiqueta
         * correspiende de la interfaz
         * emisora y receptora.
         * 
         * @param nick_usuario El apodo ingresado por el usuario.
         * @param n_nick       La etiqueta que muestra el apodo ingresado.
         * @param nickname     La etiqueta para mostrar el apodo en la interfaz.
         */
        String nick_usuario = JOptionPane.showInputDialog("Enter your Nickname: ");
        JLabel n_nick = new JLabel(nick_usuario);
        n_nick.setBounds(60, 60, 200, 30);
        add(n_nick);
        nickname = new JLabel();
        nickname.setText(nick_usuario);
        add(nickname);

        // Instrucción que el usuario debe realizar #2
        label4 = new JLabel("IP direction");
        label4.setBounds(210, 40, 100, 30);
        add(label4);

        // Espacio para escribir la IP al que se va a enviar el mensaje
        ip_user = new JTextField();
        ip_user.setBounds(190, 65, 100, 30);
        add(ip_user);

        // Se establece espacio para escribir el mensaje
        textArea = new JTextArea();
        textArea.setBounds(73, 475, 240, 30);
        add(textArea);

        // Se agrega el espacio para visualizar el chat
        chat_cliente = new JTextArea();
        chat_cliente.setBounds(23, 110, 340, 350);
        chat_cliente.setEditable(false);
        add(chat_cliente);

        // Se crea Botón de cerrar
        Font font = new Font("Arial", Font.BOLD, 30);

        boton1 = new JButton("<");
        boton1.setBounds(5, 5, 60, 30);
        boton1.setFont(font);
        boton1.setMargin(new Insets(0, 0, 0, 0));
        boton1.setFocusPainted(false);
        add(boton1);
        boton1.addActionListener(this);

        // Se crea el botón de enviar
        boton2 = new JButton(">>");
        boton2.setBounds(330, 475, 50, 30);
        boton2.setFont(font);
        boton2.setMargin(new Insets(0, 0, 0, 0));
        boton2.setFocusPainted(false);
        add(boton2);
        boton2.addActionListener(this);

        /* Se crea etiqueta encabezado */
        label1 = new JLabel("chaTec");
        label1.setBounds(0, 0, 400, 40);
        label1.setBackground(Color.DARK_GRAY);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        label1.setFont(font);
        add(label1);

        /* Se crea etiqueta parte inferior #2 */
        label2 = new JLabel("Chat");
        label2.setBounds(0, 471, 400, 40);
        label2.setBackground(Color.BLUE);
        label2.setOpaque(true);
        label2.setForeground(Color.WHITE);
        label2.setFont(font);
        add(label2);

        /**
         * Crea y inicia un nuevo hilo para ejecutar el método run() de la instancia
         * actual.
         * Este método se utiliza para ejecutar tareas en paralelo y en segundo plano
         * 
         * @param this Una instancia de la clase actual que implementa la interfaz
         *             Runnable.
         * @return Un nuevo hilo de ejecución que ejecutará el método run() de la
         *         instancia actual.
         * @see Runnable
         */
        Thread mihilo = new Thread(this);
        // Crea un nuevo hilo y lo inicializa con la instancia actual (this)
        mihilo.start();
    }

    /**
     * Maneja eventos generados por componentes de la interfaz de usuario.
     * 
     * @param evento El objeto de evento que contiene información sobre el evento.
     */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == boton1) {
            // Si el evento proviene del botón1, se cierra la aplicación.
            System.exit(0);
        }
        if (evento.getSource() == boton2) {
            try {
                // Si el evento proviene del botón2, se intenta establecer una conexión a un
                // servidor.
                Socket socketClient = new Socket("192.168.2.45", 9999);

                // Se crea un objeto Paquetería y se llenan sus datos con información de la
                // interfaz.
                Paquetería datos = new Paquetería();
                datos.setNick(nickname.getText());
                datos.setIp(ip_user.getText());
                datos.setMessage(textArea.getText());

                // Se muestra de quien proviene el mensaje y el mensaje en sí en ambos extremos
                chat_cliente.append("\n" + datos.getNick() + ": " + datos.getMessage());

                // Se envían los datos al servidor a través de ObjectOutputStream.
                ObjectOutputStream paquete_datos = new ObjectOutputStream(socketClient.getOutputStream());
                paquete_datos.writeObject(datos);

                // Se cierra la conexión con el servidor.
                socketClient.close();
                // Vaciar la barra de escritura
                textArea.setText("");

                // Se maneja cualquier excepción de E/S que pueda ocurrir durante la
                // comunicación con el servidor.
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }

    }

    /**
     * Punto de entrada principal de la aplicación.
     * Crea una instancia de la clase InterfaceCliente y configura su apariencia.
     * 
     */
    public static void main(String args[]) {
        InterfaceCliente interface1 = new InterfaceCliente();
        interface1.setBounds(0, 0, 400, 550);
        interface1.setVisible(true);
        interface1.setLocationRelativeTo(null);
        interface1.setResizable(false);
    }
}

/**
 * La clase Paquetería implementa la interfaz Serializable y representa un
 * paquete de datos.
 * Cada paquete contiene información como el nombre de usuario (nick), la
 * dirección IP (ip),
 * y un mensaje de texto (message).
 */
class Paquetería implements Serializable {
    private String nick, ip, message;

    /**
     * 
     * @return El nombre de usuario del paquet
     */
    public String getNick() {
        return nick;
    }

    /**
     * 
     * @param nick Establece el nombre de usuario
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * 
     * @return La dirección IP del paquete
     */
    public String getIp() {
        return ip;
    }

    /**
     * 
     * @param ip Establece la direccion IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 
     * @return El mensaje de texto del paquete.
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message Establece el mensaje de texto
     */
    public void setMessage(String message) {
        this.message = message;
    }

}