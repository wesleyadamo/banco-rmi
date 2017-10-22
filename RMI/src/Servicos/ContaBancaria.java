package Servicos;

import java.util.Objects;

/**
 *
 * @author Stephen Hoey
 */
class ContaBancaria {

	private String nome;
	private String cpf;
	private String endereco;
	private String senha;
	private String telefone;
	private String dataNascimento;
	private double saldoContaCorrente;
	private double saldoContaPoupanca;
	private double saldoInvestimento;
	private int ID;
	private double balance;
	private boolean investimentos = false;

	private boolean loggedIn = false;

	private static double interestRate = 0.5;

	public ContaBancaria(String nome, String cpf, String endereco, String senha, String telefone,
			String dataNascimento) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.senha = senha;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getSaldoContaCorrente() {
		return saldoContaCorrente;
	}

	public void setSaldoContaCorrente(double saldoContaCorrente) {
		this.saldoContaCorrente += saldoContaCorrente;
	}

	public double getSaldoContaPoupanca() {
		return saldoContaPoupanca;
	}

	public void setSaldoContaPoupanca(double saldoContaPoupanca) {
		this.saldoContaPoupanca += saldoContaPoupanca;
	}

	public void setSaldoCorrenteSaque(double saque) {
		saldoContaCorrente -= saque;
	}

	public void setSaldoInvestimentoSaque(double saque){
		saldoInvestimento -= saque;
	}
	public void setSaldoPoupancaSaque(double saque) {
		saldoContaPoupanca -= saque;
	}

	public double getSaldoInvestimento() {
		return saldoInvestimento;
	}

	public void setSaldoInvestimento(double saldoInvestimento) {
		this.saldoInvestimento += saldoInvestimento;
	}

	// depositar na sua propria conta
	public boolean depositar(int tipoConta, double valor) {
		if (tipoConta == 1) {
			setSaldoContaCorrente(getSaldoContaCorrente() + valor);
			return true;
		} else {
			setSaldoContaPoupanca(getSaldoContaCorrente() + valor);
			return true;

		}

	}

	public double getInterestRate() {
		return ContaBancaria.interestRate;
	}

	public synchronized void setInterestRate(double interestRate) {
		ContaBancaria.interestRate = interestRate;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean withdraw(double amount) {
		if (balance - amount >= 0) {
			balance = balance - amount;
			return true;
		} else {
			return false;
		}
	}

	public void deposit(double amount) {
		balance = balance + amount;
	}

	public double calcInterest() {
		return balance * ContaBancaria.interestRate;
	}

	public double[] rendimentoPoupanca() {
		double[] saldo = new double[3];
		double valorAplicado = saldoContaPoupanca;
		double taxa = 0.005;
		for (int i = 1; i <= 12; i++) {
			valorAplicado += valorAplicado * taxa;
			if (i == 3)
				saldo[0] = valorAplicado;
			if (i == 6)
				saldo[1] = valorAplicado;
			if (i == 12)
				saldo[2] = valorAplicado;

		}

		return saldo;

	}

	public double[] rendimentoRendaFixa() {
		double[] saldo = new double[3];
		double valorAplicado = saldoInvestimento;
		double taxa = 0.015;
		for (int i = 1; i <= 12; i++) {
			valorAplicado += valorAplicado * taxa;
			if (i == 3)
				saldo[0] = valorAplicado;
			if (i == 6)
				saldo[1] = valorAplicado;
			if (i == 12)
				saldo[2] = valorAplicado;

		}

		return saldo;

	}

	public boolean isInvestimentos() {
		return investimentos;
	}

	public void setInvestimentos(boolean investimentos) {
		this.investimentos = investimentos;
	}

}
