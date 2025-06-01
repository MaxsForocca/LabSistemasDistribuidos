# app/service.py

from spyne import Application, rpc, ServiceBase, Unicode, Float, Integer, Iterable
from spyne.protocol.soap import Soap11

class VentasService(ServiceBase):

    @rpc(Unicode, Float, Integer, _returns=Unicode)
    def realizar_venta(ctx, producto, precio, cantidad):
        """
        Realiza una venta de producto
        """
        from app.utils import validar_datos, registrar_venta, obtener_todas_las_ventas, buscar_producto
        
        valido, mensaje = validar_datos(producto, precio, cantidad)
        if not valido:
            return f"Error: {mensaje}"
        
        try:
            venta = registrar_venta(producto, precio, cantidad)
            return f"Venta registrada: {venta.producto.nombre} x{venta.cantidad} = S/{venta.total:.2f}"
        except Exception as e:
            return f"Error al procesar la venta: {str(e)}"

    @rpc(_returns=Iterable(Unicode))
    def obtener_ventas(ctx):
        """
        Obtiene la lista de todas las ventas realizadas
        """
        from app.utils import obtener_todas_las_ventas
        
        try:
            ventas = obtener_todas_las_ventas()
            if not ventas:
                return ["No hay ventas registradas"]
            
            return [f"{v.producto.nombre} x{v.cantidad} = S/{v.total:.2f}" for v in ventas]
        except Exception as e:
            return [f"Error al obtener ventas: {str(e)}"]

    @rpc(Unicode, _returns=Unicode)
    def consultar_producto(ctx, nombre_producto):
        """
        Consulta información de un producto
        """
        from app.utils import buscar_producto
        
        try:
            producto = buscar_producto(nombre_producto)
            if producto:
                return f"Producto: {producto.nombre} - Precio: S/{producto.precio:.2f}"
            else:
                return f"Producto '{nombre_producto}' no encontrado"
        except Exception as e:
            return f"Error al consultar producto: {str(e)}"

# Definir aplicación SOAP
application = Application(
    [VentasService],
    tns='ventas.soap.service',
    name='VentasSOAPService',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11()
)