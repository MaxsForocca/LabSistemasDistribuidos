#delete carpetas
#
import os
import shutil

folders = ["Propuestos", "Resueltos"]
# Crear la carpeta principal
for i in range(1,10,1):
    nombre_carperta_Lab = "Laboratorio" + str(i)
    if os.path.exists(nombre_carperta_Lab):
        shutil.rmtree(nombre_carperta_Lab)
        print(f"Carpeta eliminada: {nombre_carperta_Lab}")
    else:
        print(f"La carpeta no existe: {nombre_carperta_Lab}")