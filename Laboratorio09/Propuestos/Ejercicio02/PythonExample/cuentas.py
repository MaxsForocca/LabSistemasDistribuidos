import mysql.connector
from mysql.connector import Error

def main():
    try:
        connection = mysql.connector.connect(
            host='localhost',
            database='cuentasbancarias',
            user='usuario',
            password='password'
        )

        if connection.is_connected():
            print("Conexion exitosa a la base de datos")

            cursor = connection.cursor()

            # Desactivar autocommit para iniciar la transaccion
            connection.autocommit = False

            # 1. Insertar banco
            cursor.execute("INSERT INTO bancos (nombre, direccion) VALUES (%s, %s)", 
                           ("Banco Python", "Av. Central 789"))
            banco_id = cursor.lastrowid
            print("1er INSERT en tabla 'bancos'")
            print("ID del banco insertado:", banco_id)

            # 2. Insertar titular
            cursor.execute("INSERT INTO titulares (dni, nombre) VALUES (%s, %s)", 
                           ("99998888", "Grupo3"))
            print("2do INSERT en tabla 'titulares'")

            # 3. Insertar cuenta
            cursor.execute("INSERT INTO cuentas (dni_titular, banco_id, saldo) VALUES (%s, %s, %s)", 
                           ("99998888", banco_id, 2500.0))
            print("3er INSERT en tabla 'cuentas'")

            # 4. Intentar actualizar una cuenta inexistente (simula error)
            cursor.execute("UPDATE cuentas SET saldo = saldo - %s WHERE id = %s", 
                           (100.0, 999))  # ID 999 no existe

            if cursor.rowcount == 0:
                raise Exception("La cuenta a debitar no existe. Simulando error para rollback.")

            # Confirmar la transaccion si todo salio bien
            connection.commit()
            print("Transaccion completada con exito.")

    except Exception as e:
        print(f"ERROR: {e}")
        if connection:
            connection.rollback()
            print("Rollback ejecutado.")

    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("Conexion cerrada.")

if __name__ == "__main__":
    main()
