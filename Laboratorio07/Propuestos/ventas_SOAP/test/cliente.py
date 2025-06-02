from zeep import Client

wsdl = 'http://127.0.0.1:8000/?wsdl'

client = Client(wsdl=wsdl)

# Probar realizar_venta
respuesta_venta = client.service.realizar_venta("Laptop", 3500.0, 1)
print(">> Resultado de realizar_venta:")
print(respuesta_venta)

# Probar obtener_ventas
ventas = client.service.obtener_ventas()
print("\n>> Resultado de obtener_ventas:")
for venta in ventas:
    print(venta)

# Probar consultar_producto
info_producto = client.service.consultar_producto("Laptop")
print("\n>> Resultado de consultar_producto:")
print(info_producto)
