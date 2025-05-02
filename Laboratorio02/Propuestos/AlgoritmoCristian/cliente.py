# Simular el algoritmo de Cristian para sincronización de tiempo 
# Cliente de tiempo UTC
import socket
import time
from datetime import datetime

# formatear tiempo en dd/mm/yyyy HH:MM:SS
def formatear_tiempo(time):
    return datetime.fromtimestamp(time).strftime("%d/%m/%Y %H:%M:%S.%f")

def cliente(host='localhost', puerto=12345):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s: # crear socket TCP de cliente
        s.connect((host, puerto)) # conectar al servidor
        Tr = time.time() # tiempo de envio de solicitud Tr
        print(f"Hora del cliente (Tr): {formatear_tiempo(Tr)}")
        s.sendall(b'mr') # enviar solicitud al servidor
        datos = s.recv(1024) # recibir hora del servidor Tutc
        Tt = time.time() # tiempo de recepcion de respuesta Tt
        print(f"Hora del cliente (Tt): {formatear_tiempo(Tt)}")

    Tutc = float(datos.decode())
    RTT = Tt - Tr
    Tcliente = Tutc + RTT / 2

    print(f"Hora del servidor (Tutc): {formatear_tiempo(Tutc)}")
    print(f"RTT estimado: {RTT:.6f} segundos")
    print(f"Hora sincronizada estimada (Tcliente): {formatear_tiempo(Tcliente)}")

if __name__ == "__main__":
    cliente()
