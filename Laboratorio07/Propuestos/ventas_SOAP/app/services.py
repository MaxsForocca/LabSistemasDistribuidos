# app/service.py

from spyne import Application, rpc, ServiceBase, Unicode, Float, Integer, Iterable
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication

from app.utils import validar_datos, registrar_venta, listar_ventas

class VentasService(ServiceBase):

    @rpc(Unicode, Float, Integer, _returns=Unicode)
    def realizar_venta(ctx, producto, precio, cantidad):
        valido, mensaje = validar_datos(producto, precio, cantidad)
        if not valido:
            return f"Error: {mensaje}"
        venta = registrar_venta(producto, precio, cantidad)
        return f"Venta registrada: {venta.producto.nombre} x{venta.cantidad} = S/{venta.total:.2f}"

    @rpc(_returns=Iterable(Unicode))
    def listar_ventas(ctx):
        ventas = listar_ventas()
        return [f"{v.producto.nombre} x{v.cantidad} = S/{v.total:.2f}" for v in ventas]


# Definir aplicación SOAP
application = Application(
    [VentasService],
    tns='ventas.soap',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11()
)

# Servidor WSGI
if __name__ == '__main__':
    from wsgiref.simple_server import make_server
    print("Servidor SOAP escuchando en http://127.0.0.1:8000")
    server = make_server('127.0.0.1', 8000, WsgiApplication(application))
    server.serve_forever()
