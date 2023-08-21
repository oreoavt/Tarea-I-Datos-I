
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
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Definición de la clase que contiene la integración del diseño de la interfaz,
 * y todos los elementos que la componen,
 * además del comportamiento de los dos botones e instrcciones para el usuario
 * disponibles en la interfaz,
 * y el empaquetamiento del mensaje para ser mostrado a través del servidor
 * hacia la computadora receptora
 */
public class InterfaceServidor extends JFrame implements ActionListener, Runnable {
    private JTextArea areaTexto;
    private JLabel label1;
    private JLabel label2;
    JButton boton1;
    JButton boton2;
    Paquetería paquete_recibido;

    /*
     * se establece
     * un constructor
     * y se
     * agrega etiqueta
     */

    public InterfaceServidor() {
        setLayout(null);

        /* Se crea componente JTextArea */
        areaTexto = new JTextArea();
        areaTexto.setBounds(10, 70, 380, 400);
        areaTexto.setEditable(false);
        add(areaTexto);

        /* Se crea Botón de cerrar */
        Font font = new Font("Arial", Font.BOLD, 30);
        boton1 = new JButton("<");
        boton1.setBounds(0, 5, 60, 30);
        boton1.setFont(font);
        boton1.setMargin(new Insets(0, 0, 0, 0));
        boton1.setFocusPainted(false);
        add(boton1);
        boton1.addActionListener(this);

        /* Se crea el botón de enviar */
        boton2 = new JButton(">");
        boton2.setBounds(320, 475, 60, 30);
        boton2.setFont(font);
        boton2.setMargin(new Insets(0, 0, 0, 0));
        boton2.setFocusPainted(false);
        add(boton2);
        boton2.addActionListener(this);

        /* Se crea etiqueta #1 "chaTec" */
        label1 = new JLabel("chaTec");
        label1.setBounds(0, 0, 400, 40);
        label1.setBackground(Color.DARK_GRAY);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        label1.setFont(font);
        add(label1);

        /* Se crea la etiqueta #2 "Servidor" */
        Font font2 = new Font("Arial", Font.BOLD, 18);
        label2 = new JLabel("Servidor");
        label2.setBounds(0, 44, 400, 18);
        label2.setBackground(Color.LIGHT_GRAY);
        label2.setOpaque(true);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setForeground(Color.BLACK);
        label2.setFont(font2);
        add(label2);

        /**
         * Hilo para la ejecución del método run de la instancia
         * Implementado para ejecutar tareas en segundo plano y en paralelo con el envío
         * de los mensajes, esto es, la comunicación constante de las interfaces de los
         * usuarios con el servidor en un bucle infinito
         * 
         */
        Thread mihilo = new Thread(this);
        mihilo.start();

    }

    /**
     * Maneja eventos generados por componentes de la interfaz de usuario
     * 
     * @param evento guarda la acción ejecutada por el usuario
     */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == boton1) {
            // Si el evento proviene del botón "<", se cierra la aplicación.
            System.exit(0);
            // Linea que puede ser comentada. Testeo del funcionamiento correcto del botón
            // de envío
        } else if (evento.getSource() == boton2) {
            System.out.print("Hola");

        }
    }

    /**
     * Punto de entrada principal de la aplicación.
     * 
     * @param inferface1 Crea una instancia de la clase InterfaceCliente y configura
     *                   su apariencia.
     * 
     */
    public static void main(String args[]) {
        InterfaceServidor interface1 = new InterfaceServidor();
        interface1.setBounds(0, 0, 400, 550);
        interface1.setVisible(true);
        interface1.setLocationRelativeTo(null);
        interface1.setResizable(false);
    }

    /**
     * Se implementa la escucha del servidor para recepción de mensajes.
     * 
     * @param servidor         Se establece la variable que conecta el servidor con
     *                         el
     *                         usuario a través del puerto seleccionado.
     * @param nick             Dato tipo String que almacena el username introducido
     *                         por el
     *                         usuario
     * @param ip               Dato tipo String que almacena el ip introducido por
     *                         el
     *                         usuario,cuya dirección pertenece al destinatario
     * @param message          Dato tipo String que almacena el mensaje introducido
     *                         por el
     *                         usuario que será recibido por el otro extremo
     *                         (receptor).
     * @param miSocket         Crea el serverSocket en espera de conexiones
     *                         entrantes
     * @param paquete_datos    Crea un ObjectInputStream para leer los datos
     *                         provenientes del socket
     * @param paquete_recibido Lee los objetos desde el flujo de entrada
     */
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9999);
            String nick, ip, message;
            Paquetería recibido;

            while (true) {
                Socket miSocket = servidor.accept();
                ObjectInputStream paquete_datos = new ObjectInputStream(miSocket.getInputStream());
                paquete_recibido = (Paquetería) paquete_datos.readObject();

                nick = paquete_recibido.getNick(); // Extrae el valor de "nick" de "paquete_recibido" y lo asigna a la
                                                   // variable "nick"
                ip = paquete_recibido.getIp(); // Extrae el valor de "ip" de "paquete_recibido" y lo asigna a la
                                               // variable "ip"
                message = paquete_recibido.getMessage(); // Extrae el valor de "message" de "paquete_recibido" y lo
                                                         // asigna a la variable "message"

                // Asigna la estructura que será mostrada en el area de texto en el servidor
                areaTexto.append("\n" + nick + ": " + message + " para " + ip);

                Socket enviaDestinatario = new Socket(ip, 9090); // Crea el client socket e intenta conectar a dicho
                                                                 // puerto
                // Stream usado para enviar objetos sobre la red
                ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
                // Escribe el" paquete_recibido" objeto hacia el "paqueteReenvio". Serializa el
                // objeto para enviarlo
                paqueteReenvio.writeObject(paquete_recibido);
                // Vacia los datos restantes y libera los recursos asociados con la transmisión.
                paqueteReenvio.close();
                // Termina la conexión apropiadamente para liberar recursos
                enviaDestinatario.close();
                // Limpia los recursos del servidor asociada con el manejo de los datos
                // entrantes
                miSocket.close();
            }
            // Manejo de excepciones, se muestran al usuario
        } catch (IOException | ClassNotFoundException error) {
            error.printStackTrace();
        }

    }

}