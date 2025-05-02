# Simular el algoritmo de Cristian para sincronización de tiempo 
# Servidor de tiempo UTC
import socket
import time
from datetime import datetime

# formatear tiempo en dd/mm/yyyy HH:MM:SS
def formatear_tiempo(time):
    return datetime.fromtimestamp(time).strftime("%d/%m/%Y %H:%M:%S.%f")

def servidor(host='localhost', puerto=12345):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((host, puerto))
    # escuchar conexiones entrantes
    s.listen()
    print(f"Servidor escuchando en {host}:{puerto}")
    
    while True:
        # aceptar una conexion; socket de comunicacion y direccion del cliente
        conn, addr = s.accept()
        # manejar la conexion con el cliente
        with conn:
            print(f"Conexión desde {addr}")
            datos = conn.recv(1024) # recibir datos del cliente
            if not datos:
                continue
            tiempo_utc = time.time() # obtener tiempo UTC actual del servidor
            print(f"Tiempo UTC del servidor: {formatear_tiempo(tiempo_utc)}")
            conn.sendall(str(tiempo_utc).encode()) # enviar tiempo UTC al cliente
        break # salir del bucle después de una conexión 
    s.close()
    print("Socket cerrado correctamente.")

if __name__ == "__main__":
    servidor()
