# Proyecto SOAP - ventas_soap

Este proyecto implementa un servicio SOAP simple para procesar ventas de productos, utilizando Python, Spyne y wsgiref.


## Estructura

- `server.py`: servidor SOAP con Spyne y wsgiref.
- `requirements.txt`: dependencias necesarias.
- `README.md`: documentación inicial del proyecto.

## Cómo ejecutar el servidor

### Consideraciones Adicionales
No olvidar activar su entorno virtual si esta trabajando con uno. O si no desea que se instale en todo su sistema las librerias de requirements.txt y quiere que solo tenerlo en sus entorno.
- `Crear entorno virtual`: Con virtualenv
```bash
python -m virtualenv env
```

```bash
pip install -r requirements.txt
python server.py
