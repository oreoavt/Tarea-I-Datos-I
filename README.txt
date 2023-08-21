El objetivo de este chat es implemetar una comunicación entre clientes por medio de un servidor a través de la dirección ip del destinatario. 
A continuación se va a detallar la forma de ejecutar el chat y las restricciones que presenta.

Pasos para ejecutarlo.
1. Se requiere tener el archivo .jar InterfaceCliente instalado en diferentes maquinas físicas o virtuales y conectadas a la misma red LAN 
para que funcione la conexión.
2. Debe haber una máquina central que sea el servidor, se debe conocer la dirección ipv4 de esta para poder ingresarlo en los clientes
cuando se solicite. Para obtener esta dirección ip se debe ingresar al simbolo del sistema e ingresar el comando "ipconfig".
3. Una vez obtenida la dirección se ejecuta el archivo .jar de InterfaceServidor en la máquina asignada para este fin.
4. Desde las demás máquinas(físicas o virtuales), ejecutar el archivo.jar InterfaceClient. Importante: se debe tener instalado java en la computadora.
5. Se la solicitará la dirección ip del servidor. Coloquela y presione en aceptar.
6. Posteriormente se solicitará su nickname(nombre de usuario) con el fin de que se identifique en las comunicaciones dentro de la red.
7. Como paso final se debe ingresar en el apartado de "ip direction" la dirección de la computadora con la cual nos deseamos comunicar.
Para obtener esta dirección seguimos los mismos pasos que en el paso 2, en la maquina de destino. Una vez listo, está establecida la conexión 
solo queda enviar el mensaje que deseamos.

Nota: Para poder responder los mensajes desde la computadora de destino hacia la computadora inicial, se deben seguir los mismos pasos para establecer la
comunicación.

Limitaciones encontradas:
1. Cuando se ejecuta el programa de manera local el envío de mensajes solo sirve en una dirección y no se puede responder. Se recomienda utilizar en diferentes
maquinas, ya  sean físicas o virtuales conectadas a la misma red.
2. Incapacidad para lograr conexiones automáticas y de identificación.
3. Si no se siguen adecuadamente los pasos puede que no se logre establecer la conexión.
