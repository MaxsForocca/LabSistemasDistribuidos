from spyne import Application, rpc, ServiceBase, Unicode, Integer
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication

class VentasService(ServiceBase):

    @rpc(Unicode, Integer, _returns=Unicode)
    def procesar_venta(ctx, producto, cantidad):
        return f"Venta procesada: {cantidad} unidad(es) de '{producto}'"

# Definir la aplicación SOAP
application = Application(
    [VentasService],
    tns='ventas.soap',
    in_protocol=Soap11(validator='lxml'),
    out_protocol=Soap11()
)

# Servidor WSGI
if __name__ == '__main__':
    from wsgiref.simple_server import make_server

    server = make_server('127.0.0.1', 8000, WsgiApplication(application))
    print("Servidor SOAP escuchando en http://127.0.0.1:8000")
    server.serve_forever()
