#Crear carpetas
import os
folders = ["Propuestos", "Resueltos"]


def nombre_Carpeta(i):
    return "Laboratorio0" + str(i) if i < 10 else "Laboratorio" + str(i)

# Crear la carpeta principal
for i in range(1,10,1):
    nombre_carperta_Lab = nombre_Carpeta(i)
    # Comprobar si la carpeta ya existe
    if not os.path.exists(nombre_carperta_Lab):
        os.makedirs(nombre_carperta_Lab)
        # Crear las subcarpetas dentro de la carpeta principal
        for folder in folders:
            folder_path = os.path.join(nombre_carperta_Lab, folder)
            if not os.path.exists(folder_path):
                os.makedirs(folder_path)
                print(f"Carpeta creada: {folder_path}")
            else:
                print(f"La carpeta ya existe: {folder_path}")
    else:
        print(f"La carpeta ya existe: {nombre_carperta_Lab}")


