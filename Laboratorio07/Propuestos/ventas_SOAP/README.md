# Proyecto SOAP - ventas_soap

Este proyecto implementa un servicio SOAP simple para procesar ventas de productos, utilizando Python, Spyne y wsgiref.

## Funcionalidades del servicio

El servicio SOAP expone las siguientes operaciones:

- `realizar_venta(producto: str, precio: float, cantidad: int)`  
  Registra una nueva venta. Si el producto no existe, se agrega automáticamente.

- `obtener_ventas()`  
  Devuelve una lista con todas las ventas registradas.

- `consultar_producto(nombre_producto: str)`  
  Busca un producto por nombre y devuelve su información.

## Estructura

- `server.py`: servidor SOAP con Spyne y wsgiref.
- `requirements.txt`: dependencias necesarias.
- `README.md`: documentación inicial del proyecto.

### Consideraciones Adicionales (Opcional)
No olvidar activar su entorno virtual si esta trabajando con uno. O si no desea que se instale en todo su sistema las librerias de requirements.txt y quiere que solo tenerlo en sus entorno.
- `Crear entorno virtual`: Con virtualenv
```bash
python -m virtualenv env
```
- `Activar entorno virtual`: Activar env
```bash
.\env\Scripts\activate
```
- `Desactivar entorno virtual`: Desactivar env
```bash
deactivate
```

## Cómo ejecutar el servidor

```bash
pip install -r requirements.txt
python server.py
