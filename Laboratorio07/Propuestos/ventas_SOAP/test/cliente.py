# test/client.py
from zeep import Client

WSDL_URL = 'http://127.0.0.1:8000/?wsdl'

client = Client(wsdl=WSDL_URL)

def mostrar_resultado(nombre, resultado):
    print(f">> {nombre}:\n{resultado}\n{'-'*40}")

# Prueba 1: Venta válida
resp1 = client.service.realizar_venta("Tablet", 1000.0, 2)
mostrar_resultado("Venta válida", resp1)

# Prueba 2: Precio negativo
resp2 = client.service.realizar_venta("TV", -1500.0, 1)
mostrar_resultado("Precio negativo", resp2)

# Prueba 3: Cantidad cero
resp3 = client.service.realizar_venta("Mouse", 50.0, 0)
mostrar_resultado("Cantidad cero", resp3)

# Prueba 4: Nombre vacío
resp4 = client.service.realizar_venta("", 100.0, 1)
mostrar_resultado("Nombre vacío", resp4)

# Prueba 5: Producto ya existente
resp5 = client.service.consultar_producto("Tablet")
mostrar_resultado("Consultar producto existente", resp5)

# Prueba 6: Producto inexistente
resp6 = client.service.consultar_producto("Helicoptero")
mostrar_resultado("Consultar producto inexistente", resp6)

# Prueba 7: Ver ventas acumuladas
resp7 = client.service.obtener_ventas()
mostrar_resultado("Obtener ventas", "\n".join(resp7))
