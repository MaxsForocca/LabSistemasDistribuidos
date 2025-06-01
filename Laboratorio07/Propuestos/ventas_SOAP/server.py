# server.py

from wsgiref.simple_server import make_server
from spyne.server.wsgi import WsgiApplication
from app.services import application

if __name__ == '__main__':
    server = make_server('127.0.0.1', 8000, WsgiApplication(application))
    
    print("Servidor SOAP de Ventas Iniciado")
    print("URL del servicio: http://127.0.0.1:8000")
    print("WSDL disponible en: http://127.0.0.1:8000/?wsdl")
    print("Presiona Ctrl+C para detener el servidor")
    
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("Servidor detenido por el usuario")
        server.shutdown()