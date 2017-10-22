package Servicos;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Stephen Hoey
 */
public class ContaImp extends UnicastRemoteObject implements ContaInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, ContaBancaria> contas = new HashMap<String, ContaBancaria>();
	private int ID;

	public ContaImp() throws RemoteException {
		super();
	}


	public String login(int id, String password) throws RemoteException {
		Set<String> chaves = contas.keySet();
		ContaBancaria temp;
		for (String chave : chaves) {
			temp = contas.get(chave);
			if (temp.getID() == id && temp.getSenha().equals(password))
				return temp.getCpf();
		}

		return "";
	}



	public int getID() {
		return ID;
	}

	public void setID() {
		ID++;
	}

	// cria conta bancaria
	@Override
	public int criarConta(String nome, String cpf, String endereco, String dataNascimento, String telefone,
			String senha) throws RemoteException {

		ContaBancaria b = new ContaBancaria(nome, cpf, endereco, senha, telefone, dataNascimento);

		// verifica se já existe a conta bancária
		if (!contas.containsKey(cpf)) {

			b.setID(getID());
			contas.put(cpf, b);
			setID();
			return b.getID();

		}

		return -1;
	}

	// obtem a conta
	public ContaBancaria contains(int ID) {
		Set<String> chaves = contas.keySet();
		ContaBancaria temp;
		for (String chave : chaves) {
			temp = contas.get(chave);
			if (temp.getID() == ID)
				return temp;
		}

		return null;
	}

	// deposita
	@Override
	public double depositar(int contaTipo, double valor, int ID) throws RemoteException {
		ContaBancaria temp = contains(ID);
		if (temp != null) {
			if (contaTipo == 1) {
				temp.setSaldoContaCorrente(valor);
				return temp.getSaldoContaCorrente();
			} else if (contaTipo == 2) {
				temp.setSaldoContaPoupanca(valor);
				return temp.getSaldoContaPoupanca();
			} else if (contaTipo == 3){
				temp.setSaldoInvestimento(valor);
				return temp.getSaldoInvestimento();
			}

		}
		return -1;
	}

	@Override
	public double[] saldo(String cpfCliente) throws RemoteException {

		ContaBancaria cb = contas.get(cpfCliente);
		double[] saldos = { cb.getSaldoContaCorrente(), cb.getSaldoContaPoupanca(), cb.getSaldoInvestimento() };
		return saldos;
	}

	@Override
	public boolean sacar(int contaTipo, double valor, String cpf) throws RemoteException {

		ContaBancaria cb = contas.get(cpf);
		// corrente
		if (contaTipo == 1) {
			double saldo = cb.getSaldoContaCorrente();
			System.out.println("saldo com saque: " + (saldo - valor));
			boolean r = saldo >= valor ? true : false;
			System.out.println(r);

			if (r) {
				cb.setSaldoCorrenteSaque(valor);

			}
			return r;

			// poupança
		} else if (contaTipo == 2) {
			double saldo = cb.getSaldoContaPoupanca();
			System.out.println("saldo com saque: " + (saldo - valor));

			boolean r = saldo >= valor ? true : false;
			System.out.println(r);
			if (r) {
				cb.setSaldoPoupancaSaque(valor);

			}
			return r;

		} else if(contaTipo ==3){
			double saldo = cb.getSaldoInvestimento();

			boolean r = saldo >= valor ? true : false;
			if (r) {
				cb.setSaldoInvestimentoSaque(valor);

			}
			return r;
			
			
		}

		return false;
	}

	@Override
	public int transferirContasCliente(String cpf, int tipo, double valor, int id) throws RemoteException {
		// corrente para poupança
		if (tipo == 1) {
			sacar(1, valor, cpf);
			depositar(2, valor, id);
			return 1;

			// poupança para corrente
		} else if (tipo == 2) {
			sacar(2, valor, cpf);
			depositar(1, valor, id);
		}

		return 1;
	}

	@Override
	public int transferirEntreClientes(String cpf, int tipo1, int tipo2, double valor, int id) throws RemoteException {
		// corrente para poupança

		ContaBancaria temp = contains(id);

		if (temp != null) {
			// corrente para corrente
			if (tipo1 == 1 && tipo2 == 1) {
				sacar(1, valor, cpf);
				depositar(1, valor, id);
				return 1;

				// poupança para corrente
			} else if (tipo1 == 1  && tipo2==2) {
				sacar(1, valor, cpf);
				depositar(2, valor, id);
				return 1;
			} else if(tipo1 == 2  && tipo2== 1){
				sacar(2, valor, cpf);
				depositar(1,valor, id);
				return 1;
			} else if( tipo1 ==2 && tipo2==2){
				sacar(2, valor, cpf);
				depositar(2,valor,id);
				return 1;
			}

		}
		
		return -1;
	}

	public double [] projecaoInvestimento(int tipo, String cpf) throws RemoteException {
		ContaBancaria temp =  contas.get(cpf);
		// poupança
		if(tipo ==1){
			return temp.rendimentoPoupanca();
		}
		else
			return temp.rendimentoRendaFixa();
	}

	@Override
	public boolean investido(String key) throws RemoteException {
		
		ContaBancaria temp = contas.get(key);
		return temp.isInvestimentos();
	}

	@Override
	public boolean setInvestimento(String key) throws RemoteException {
		
		contas.get(key).setInvestimentos(true);
		return false;
	}

}
