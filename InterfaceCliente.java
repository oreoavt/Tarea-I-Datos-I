
/*
Instituto Tecnológico de Costa Rica
Trabajo extraclase #1
Implementación de un chat instantáneo
Estudiantes: Bryan Monge y Emmanuel Calvo
Profesor: Leonardo Araya
Ingeniería en Computadores
II Semestre 2023
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

public class InterfaceCliente extends JFrame implements ActionListener, Runnable {

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

    private String serverIP;

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

    /* se establece un constructor y se agrega etiqueta */
    public InterfaceCliente() {
        setLayout(null);

        // Se crea la instrucción que el usuario debe realizar
        label3 = new JLabel("Username:");
        label3.setBounds(60, 40, 200, 30);
        add(label3);

        serverIP = JOptionPane.showInputDialog("Enter the Server IP Address: ");

        // Espacio para escribir el username
        String nick_usuario = JOptionPane.showInputDialog("Enter your Nickname: ");

        JLabel n_nick = new JLabel(nick_usuario);
        n_nick.setBounds(60,60,200,30);
        add(n_nick);
        nickname = new JLabel();
        nickname.setText(nick_usuario);
        add(nickname);

        // Se crea la instrucción que el usuario debe realizar
        label4 = new JLabel("IP direction");
        label4.setBounds(210, 40, 100, 30);
        add(label4);

        // Se crea la instrucción que el usuario debe realizar
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

        /* Se crea Botón de cerrar */
        Font font = new Font("Arial", Font.BOLD, 30);

        boton1 = new JButton("<");
        boton1.setBounds(5, 5, 60, 30);
        boton1.setFont(font);
        boton1.setMargin(new Insets(0, 0, 0, 0));
        boton1.setFocusPainted(false);
        add(boton1);
        boton1.addActionListener(this);

        /* Se crea el botón de enviar */
        boton2 = new JButton(">>");
        boton2.setBounds(330, 475, 50, 30);
        boton2.setFont(font);
        boton2.setMargin(new Insets(0, 0, 0, 0));
        boton2.setFocusPainted(false);
        add(boton2);
        boton2.addActionListener(this);

        /* Se crea etiqueta #1 */
        label1 = new JLabel("chaTec");
        label1.setBounds(0, 0, 400, 40);
        label1.setBackground(Color.DARK_GRAY);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setForeground(Color.WHITE);
        label1.setFont(font);
        add(label1);

        /* Se crea etiqueta #2 */
        label2 = new JLabel("Chat");
        label2.setBounds(0, 471, 400, 40);
        label2.setBackground(Color.BLUE);
        label2.setOpaque(true);
        label2.setForeground(Color.WHITE);
        label2.setFont(font);
        add(label2);

        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    /* Eventos de los botones */
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == boton1) {
            System.exit(0);
        }
        if (evento.getSource() == boton2) {
            try {
                Socket socketClient = new Socket(serverIP, 9999);
                Paquetería datos = new Paquetería();
                datos.setNick(nickname.getText()); // Utiliza nickname.getText() en lugar de nick.getNick()
                datos.setIp(ip_user.getText()); // Utiliza ip_user.getText() en lugar de ip.getIp()
                datos.setMessage(textArea.getText());
                chat_cliente.append("\n" + datos.getNick() + ": " + datos.getMessage());

                ObjectOutputStream paquete_datos = new ObjectOutputStream(socketClient.getOutputStream());

                paquete_datos.writeObject(datos);
                socketClient.close();
                /*Vaciar la barra de escritura */
                textArea.setText("");
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }

    }

    /* Se implementan las configuraciones de la interfaz */
    public static void main(String args[]) {
        InterfaceCliente interface1 = new InterfaceCliente();
        interface1.setBounds(0, 0, 400, 550);
        interface1.setVisible(true);
        interface1.setLocationRelativeTo(null);
        interface1.setResizable(false);
    }
}

class Paquetería implements Serializable {
    private String nick, ip, message;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}