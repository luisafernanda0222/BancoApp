class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

class MontoNegativoException extends Exception {
    public MontoNegativoException(String mensaje) {
        super(mensaje);
    }
}

class CuentaBancaria {
    private String titular;
    private double saldo;

    public CuentaBancaria(String titular) {
        this.titular = titular;
        this.saldo = 0;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) throws MontoNegativoException {
        if (monto < 0) {
            throw new MontoNegativoException("El monto a depositar no puede ser negativo.");
        }
        saldo += monto;
        System.out.println("Se han depositado " + monto + ". Saldo actual: " + saldo);
    }

    public void retirar(double monto) throws SaldoInsuficienteException, MontoNegativoException {
        if (monto < 0) {
            throw new MontoNegativoException("El monto a retirar no puede ser negativo.");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar el retiro.");
        }
        saldo -= monto;
        System.out.println("Se han retirado " + monto + ". Saldo actual: " + saldo);
    }

    public void transferir(CuentaBancaria cuentaDestino, double monto)
            throws SaldoInsuficienteException, MontoNegativoException {
        this.retirar(monto);
        cuentaDestino.depositar(monto);
        System.out.println("Se han transferido " + monto + " a " + cuentaDestino.titular);
    }
}

public class BancoApp {
    public static void main(String[] args) {
        CuentaBancaria cuenta1 = new CuentaBancaria("luisa");
        CuentaBancaria cuenta2 = new CuentaBancaria("julia");

        try {
            cuenta1.depositar(800);
            cuenta1.retirar(100);
            cuenta1.transferir(cuenta2, 100);
            cuenta1.retirar(300);
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (MontoNegativoException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Operaciones finalizadas. Saldo de luisa: " + cuenta1.getSaldo());
            System.out.println("Operaciones finalizadas. Saldo de julia: " + cuenta2.getSaldo());
        }
    }
}
