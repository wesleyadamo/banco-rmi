package Servidor;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import Servicos.ContaImp;

public class ContaServidor {

	public static void main(String args[]) {

		try {
			
			LocateRegistry.createRegistry(1091);

			ContaImp conta = new ContaImp();

			Naming.rebind("rmi://localhost/Banco", conta);
			System.out.println("SERVIDOR EXECUTANDO NA PORTA 1090");

		} catch (Exception r) {
			System.out.println(r);
		}
	}

}
