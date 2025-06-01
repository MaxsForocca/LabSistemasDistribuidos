# app/models.py

class Producto:
    """
    Clase que representa un producto en el sistema de ventas
    """
    def __init__(self, nombre, precio):
        self.nombre = nombre
        self.precio = precio
    
    def __str__(self):
        return f"Producto: {self.nombre} - S/{self.precio:.2f}"
    
    def __repr__(self):
        return f"Producto('{self.nombre}', {self.precio})"


class Venta:
    """
    Clase que representa una venta realizada
    """
    contador_id = 1  # Para generar IDs únicos
    
    def __init__(self, producto, cantidad):
        self.id = Venta.contador_id
        Venta.contador_id += 1
        self.producto = producto
        self.cantidad = cantidad
        self.total = self.calcular_total()
    
    def calcular_total(self):
        """
        Calcula el total de la venta
        """
        return self.producto.precio * self.cantidad
    
    def __str__(self):
        return f"Venta #{self.id}: {self.producto.nombre} x{self.cantidad} = S/{self.total:.2f}"
    
    def __repr__(self):
        return f"Venta({self.producto}, {self.cantidad})"


# Base de datos simulada en memoria
PRODUCTOS_DISPONIBLES = [
    Producto("Camisa", 25.50),
    Producto("Pantalon", 45.00),
    Producto("Zapatos", 80.00),
    Producto("Chaqueta", 120.00),
    Producto("Gorra", 15.00)
]

# Lista para almacenar las ventas realizadas
VENTAS_REALIZADAS = []