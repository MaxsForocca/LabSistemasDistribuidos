# app/utils.py

from app.models import Producto, Venta, PRODUCTOS_DISPONIBLES, VENTAS_REALIZADAS


def validar_datos(nombre_producto, precio, cantidad):
    """
    Valida los datos de entrada para una venta
    
    Args:
        nombre_producto (str): Nombre del producto
        precio (float): Precio del producto
        cantidad (int): Cantidad a vender
        
    Returns:
        tuple: (bool, str) - (es_valido, mensaje_error)
    """
    # Validar que el nombre no esté vacío
    if not nombre_producto or nombre_producto.strip() == "":
        return False, "El nombre del producto no puede estar vacío"
    
    # Validar que el precio sea positivo
    if precio <= 0:
        return False, "El precio debe ser mayor a 0"
    
    # Validar que la cantidad sea positiva
    if cantidad <= 0:
        return False, "La cantidad debe ser mayor a 0"
    
    # Validar que la cantidad sea un número entero
    if not isinstance(cantidad, int):
        return False, "La cantidad debe ser un número entero"
    
    if buscar_producto(nombre_producto) == None:
            return False, "El producto no existe"
    
    return True, "Datos válidos"


def buscar_producto(nombre_producto):
    """
    Busca un producto en la lista de productos disponibles
    
    Args:
        nombre_producto (str): Nombre del producto a buscar
        
    Returns:
        Producto: El producto encontrado o None si no existe
    """
    nombre_producto = nombre_producto.strip().lower()
    
    for producto in PRODUCTOS_DISPONIBLES:
        if producto.nombre.lower() == nombre_producto:
            return producto
    
    return None


def crear_producto(nombre, precio):
    """
    Crea un nuevo producto y lo agrega a la lista
    
    Args:
        nombre (str): Nombre del producto
        precio (float): Precio del producto
        
    Returns:
        Producto: El producto creado
    """
    producto = Producto(nombre, precio)
    return producto


def registrar_venta(nombre_producto, precio, cantidad):
    """
    Registra una nueva venta en el sistema
    
    Args:
        nombre_producto (str): Nombre del producto
        precio (float): Precio del producto
        cantidad (int): Cantidad vendida
        
    Returns:
        Venta: La venta registrada
    """
    # Buscar si el producto ya existe
    producto_existente = buscar_producto(nombre_producto)
    
    if producto_existente:
        # Usar el producto existente
        producto = producto_existente
    else:
        # Crear un nuevo producto
        producto = crear_producto(nombre_producto, precio)
        PRODUCTOS_DISPONIBLES.append(producto)
    
    # Crear la venta
    venta = Venta(producto, cantidad)
    
    # Agregar la venta a la lista
    VENTAS_REALIZADAS.append(venta)
    
    return venta


def obtener_todas_las_ventas():
    """
    Obtiene todas las ventas realizadas
    
    Returns:
        list: Lista de todas las ventas
    """
    return VENTAS_REALIZADAS.copy()


def calcular_total_ventas():
    """
    Calcula el total de dinero de todas las ventas
    
    Returns:
        float: Total de todas las ventas
    """
    total = 0
    for venta in VENTAS_REALIZADAS:
        total += venta.total
    return total


def obtener_productos_disponibles():
    """
    Obtiene la lista de productos disponibles
    
    Returns:
        list: Lista de productos disponibles
    """
    return PRODUCTOS_DISPONIBLES.copy()


def contar_ventas():
    """
    Cuenta el número total de ventas realizadas
    
    Returns:
        int: Número de ventas
    """
    return len(VENTAS_REALIZADAS)