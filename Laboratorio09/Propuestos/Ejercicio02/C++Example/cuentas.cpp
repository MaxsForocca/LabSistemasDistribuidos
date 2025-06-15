#include <iostream>
#include <memory>
#include <mysqlx/xdevapi.h>

using namespace std;
using namespace mysqlx;

int main() {
    Session sess;
    try {
        // Conexion a la base de datos
        Session sess("localhost", 33060, "usuario", "password", "cuentasbancarias");
        sess.startTransaction();  // iniciar transaccion

        // Insertar banco y recuperar ID
        Schema db = sess.getSchema("cuentasbancarias");
        Table bancos = db.getTable("bancos");
        Result res1 = bancos.insert("nombre", "direccion")
                             .values("Banco 3", "Av. Central 789")
                             .execute();
        int bancoId = static_cast<int>(res1.getAutoIncrementValue());
        cout << "Banco insertado. ID generado: " << bancoId << endl;

        // Insertar titular
        Table titulares = db.getTable("titulares");
        titulares.insert("dni", "nombre")
                  .values("99998888", "Grupo4")
                  .execute();
        cout << "Titular insertado." << endl;

        // Insertar cuenta
        Table cuentas = db.getTable("cuentas");
        cuentas.insert("dni_titular", "banco_id", "saldo")
               .values("99998888", bancoId, 2500.0)
               .execute();
        cout << "Cuenta insertada." << endl;

        // Simular error con actualizacion invalida
        Result res4 = cuentas.update()
                             .set("saldo", "saldo - 100")
                             .where("id = 999")
                             .execute();
        if (res4.getAffectedItemsCount() == 0) {
            throw runtime_error("Cuenta inexistente. Forzando rollback.");
        }

        sess.commit();  // confirmar transaccion
        cout << "Transaccion completada." << endl;

    } catch (const Error &err) {
        cerr << "ERROR: " << err.what() << endl;
        try {
            mysqlx::sess.rollback();
            cerr << "Rollback ejecutado." << endl;
        } catch (...) {
            cerr << "Error al hacer rollback." << endl;
        }
    }
    return 0;
}
