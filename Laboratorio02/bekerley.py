import random

def obtener_tiempos_nodos(nodos):
	# Simula obtener el tiempo actual de cada nodo
	tiempos = {}
	for nodo in nodos:
    	tiempos[nodo] = nodos[nodo]
	return tiempos

def calcular_ajustes(tiempos):
	# Calcula el promedio y los ajustes para cada nodo
	promedio = sum(tiempos.values()) / len(tiempos)
	ajustes = {}
	for nodo, tiempo in tiempos.items():
    	ajustes[nodo] = promedio - tiempo
	return ajustes

def aplicar_ajustes(nodos, ajustes):
	# Aplica los ajustes a cada nodo
	for nodo in nodos:
    	nodos[nodo] += ajustes[nodo]

# Inicializamos los nodos con tiempos aleatorios
nodos = {
	"Nodo1": random.randint(90, 110),
	"Nodo2": random.randint(90, 110),
	"Nodo3": random.randint(90, 110),
	"Nodo4": random.randint(90, 110),
}

print("Tiempos iniciales:")
print(nodos)

# El nodo 1 sera el coordinador
coordinador = "Nodo1"

# Paso 1: Obtener tiempos
tiempos = obtener_tiempos_nodos(nodos)
print("\nTiempos recogidos por el coordinador:")
print(tiempos)

# Paso 2: Calcular ajustes
ajustes = calcular_ajustes(tiempos)
print("\nAjustes calculados (en segundos):")
print(ajustes)

# Paso 3: Aplicar ajustes
aplicar_ajustes(nodos, ajustes)
print("\nTiempos finales sincronizados:")
print(nodos)
