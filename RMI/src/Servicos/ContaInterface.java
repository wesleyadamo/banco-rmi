package Servicos;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Stephen Hoey
 */
public interface ContaInterface extends Remote {
	/*
	 * 1) Adding an account 2) Logging in to an account 3) Withdraw money from
	 * an account 4) Deposit money into an account 5) Get the balance within an
	 * account 6) Calculate the interest to be earned by the balance 7) Change
	 * the interest rate for all accounts
	 */

	public int criarConta(String nome, String cpf, String endereco, String dataNascimento, String telefone,
			String senha) throws RemoteException;

	public double[] saldo(String cpf) throws RemoteException;
	
	public int transferirContasCliente(String cpf, int tipo, double valor, int id) throws RemoteException;
	
	public int transferirEntreClientes(String cpf, int tipo1, int tipo2, double valor, int id) throws RemoteException;

	public double depositar(int contaTipo, double valor, int ID) throws RemoteException;
	
	public boolean sacar(int contaTipo, double valor, String cpf) throws RemoteException;

	public String login(int id, String senha) throws RemoteException;
	
	public double [] projecaoInvestimento(int tipo, String cpf) throws RemoteException;
	
	public boolean investido(String key) throws RemoteException;
	
	public boolean setInvestimento(String key) throws RemoteException;

}
