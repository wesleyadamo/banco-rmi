package Cliente;

import java.io.IOException;
import java.rmi.Naming;
import java.text.DecimalFormat;
import java.util.Scanner;

import Servicos.ContaInterface;

public class ContaCliente {

	static boolean investimento = false;

	@SuppressWarnings("resource")
	public static int menu() throws IOException {

		Scanner opc = new Scanner(System.in);
		Runtime.getRuntime().exec("clear");

		System.out.println("\n=====================================");
		System.out.println("\n1- DEPOSITAR");
		System.out.println("2- TRANSFERIR");
		System.out.println("3- SACAR");
		System.out.println("4- SALDO");
		if (!investimento)
			System.out.println("5- INVESTIR EM CONTA FIXA");
		System.out.println("6- VERIFICAR PROJEÇÃO DO INVESTIMENTO");
		System.out.println("0- Sair");
		System.out.print("\nESCOLHA A OPÇÃO: ");

		return opc.nextInt();

	}

	public static void menuEntrada() throws IOException {

		System.out.println("=====================================");
		System.out.println("1- CRIAR CONTA");
		System.out.println("2- LOGIN");
		System.out.print("\nEscolha a opção: ");

	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {

			Scanner input = new Scanner(System.in);

			System.setProperty("java.rmi.server.hostname", "10.130.15.237");
			ContaInterface conta = (Servicos.ContaInterface) Naming.lookup("rmi://10.130.15.237:1090/Banco");

			menuEntrada();
			int opc = input.nextInt();

			int idConta = 0;
			String cpfCliente = "";

			boolean logado = false;

			DecimalFormat df = new DecimalFormat("0.####");

			while (!logado) {
				if (opc == 1) {
					System.out.println("\n=========================================");
					System.out.println("CRIAR CONTA");
					input.nextLine(); // limpa buffer

					System.out.print("Nome:");
					String nome = input.nextLine();
					System.out.print("Cpf:");
					String cpf = input.nextLine();
					System.out.print("Nascimento:");
					String dataNascimento = input.nextLine();
					System.out.print("Endereco:");
					String endereco = input.nextLine();
					System.out.print("Telefone:");
					String telefone = input.nextLine();
					System.out.print("Senha:");
					String senha = input.nextLine();
					System.out.println("=========================================\n");
					int sucess = conta.criarConta(nome, cpf, endereco, dataNascimento, telefone, senha);
					if (sucess != -1) {
						System.out.println("\nConta criada com sucesso!");
						idConta = sucess;
						System.out.println("ID da Conta: " + sucess);
						cpfCliente = cpf;
						System.out.println();
						logado = true;
						// input.nextLine();
						opc = menu();

					} else {
						System.out.println("CPF JÁ CADASTRADO");
						menuEntrada();
						opc = input.nextInt();

					}

				} else if (opc == 2) {

					System.out.println("\tLOGIN");
					System.out.println("=========================================\n");

					System.out.println();
					System.out.print("ID: ");
					int id = input.nextInt();
					input.nextLine();
					System.out.print("Senha:");
					String senha = input.nextLine();
					System.out.println("=========================================\n");

					cpfCliente = conta.login(id, senha);
					if (!cpfCliente.isEmpty()) {
						investimento = conta.investido(cpfCliente);

						opc = menu();
						logado = true;
						idConta = id;

					} else {
						System.out.println("Dados da conta estão errados");
						System.out.println("=========================================\n");

						menuEntrada();
						opc = input.nextInt();
					}

				} else {
					menuEntrada();
					opc = input.nextInt();

				}
			}

			while (opc != 0) {

				switch (opc) {
				/*
				 * case 1:
				 * System.out.println("**************Criar Conta************");
				 * input.nextLine(); // limpar buffer
				 * 
				 * System.out.println("Nome:"); String nome = input.nextLine();
				 * System.out.println("Cpf:"); String cpf = input.nextLine();
				 * System.out.println("Nascimento:"); String dataNascimento =
				 * input.nextLine(); System.out.println("Endereco:"); String
				 * endereco = input.nextLine(); System.out.println("Telefone:");
				 * String telefone = input.nextLine();
				 * System.out.println("Senha:"); String senha =
				 * input.nextLine();
				 * System.out.println("**************************************");
				 * int sucess = h.criarConta(nome, cpf, endereco,
				 * dataNascimento, telefone, senha); if (sucess != -1) {
				 * System.out.println("Conta criada com sucesso!"); idConta =
				 * sucess; System.out.println("ID da Conta: " + sucess);
				 * cpfCliente = cpf; System.out.println(); input.nextLine(); opc
				 * = menu();
				 * 
				 * } else { System.out.println("CPF JÁ CADASTRADO");
				 * menuEntrada(); opc = input.nextInt(); } break;
				 * 
				 * case 2: System.out.println(); System.out.println("ID"); int
				 * id = input.nextInt(); input.nextLine();
				 * System.out.println("Senha"); senha = input.nextLine();
				 * 
				 * cpfCliente = h.login(id, senha); investimento =
				 * h.investido(cpfCliente); if (!cpfCliente.isEmpty()) { opc =
				 * menu(); idConta = id;
				 * 
				 * } else { System.out.println("Dados da conta estão errados");
				 * menuEntrada(); opc = input.nextInt(); }
				 * 
				 * break;
				 */

				case 1:
					System.out.println("\nDEPOSITAR");
					System.out.println("=========================================\n");

					double[] saldos = conta.saldo(cpfCliente);
					System.out.println("\nSALDO CONTA CORRENTE: " + saldos[0]);
					System.out.println("SALDO CONTA POUPANÇA " + saldos[1]);
					if (saldos[2] > 0)
						System.out.println("SALDO RENDA FIXA: " + saldos[2]);

					System.out.println("\n1- CONTA CORRENTE");
					System.out.println("2- CONTA POUPANÇA");
					if (investimento)
						System.out.println("3- RENDA FIXA");

					System.out.print("\nSELECIONE A OPÇÃO: ");
					int tipo = input.nextInt();

					System.out.print("\nVALOR: ");
					double valor = input.nextDouble();

					while (valor <= 0) {
						System.out.println("\nDIGITE UM VALOR VÁLIDO!!");
						System.out.print("VALOR: ");
						valor = input.nextDouble();
					}

					conta.depositar(tipo, valor, idConta);

					saldos = conta.saldo(cpfCliente);
					System.out.println("\nEXTRATO BANCÁRIO");
					System.out.println("-------------------------------------------\n");

					System.out.println("SALDO CONTA CORRENTE " + saldos[0]);
					System.out.println("SALDO CONTA POUPANÇA " + saldos[1]);
					if (saldos[2] > 0)
						System.out.println("SALDO RENDA FIXA: " + saldos[2]);
					opc = menu();
					break;
				// transferência
				case 2:
					System.out.println("\nTRANFERÊNCIA");
					System.out.println("=========================================\n");

					saldos = conta.saldo(cpfCliente);

					if (saldos[0] == 0 && saldos[1] == 0) {
						System.out.println("\nOPÇÃO INDISPONÍVEL");
					} else {

						System.out.println();
						System.out.println("1- ENTRE CONTAS DO CLIENTE");
						System.out.println("2- OUTRO CLIENTE");
						System.out.print("\nSELECIONE A OPÇÃO: ");
						tipo = input.nextInt();

						System.out.println();
						System.out.print("\nValor: ");
						valor = input.nextDouble();

						while (valor <= 0) {
							System.out.println("\nDIGITE UM VALOR VÁLIDO!!");
							System.out.print("VALOR: ");
							valor = input.nextDouble();
						}

						input.nextLine();

						if (tipo == 1) {
							if (saldos[0] == 0) {
								System.out.println("\nPoupança para corrente");
								System.out.println("\nConfirma a operação ? [S] [N]");
								String op = input.nextLine();

								if (op.equalsIgnoreCase("s")) {

									Object c = saldos[1] >= valor
											? conta.transferirContasCliente(cpfCliente, 2, valor, idConta) : -1;

								}

							} else if (saldos[1] == 0) {
								System.out.println("\nCorrente para poupança");
								System.out.println("\nConfirma a operação ? [S] [N]");
								String op = input.nextLine();

								if (op.equalsIgnoreCase("s")) {

									Object c = saldos[0] >= valor
											? conta.transferirContasCliente(cpfCliente, 1, valor, idConta) : -1;
								}

							} else {
								System.out.println("\n1- Corrente  para poupança");
								System.out.println("2- Poupança para corrente");

								System.out.print("\nSeleciona a opção: ");
								tipo = input.nextInt();
								input.nextLine();
								System.out.println("\nConfirma a operação ? [S] [N]");
								String op = input.nextLine();

								if (tipo == 1) {

									if (op.equalsIgnoreCase("s")) {

										Object c = saldos[0] >= valor
												? conta.transferirContasCliente(cpfCliente, 1, valor, idConta) : -1;
									}

								} else {
									if (op.equalsIgnoreCase("s")) {

										Object c = saldos[1] >= valor
												? conta.transferirContasCliente(cpfCliente, 2, valor, idConta) : -1;
									}

								}

							}

						} else if (tipo == 2) {

							System.out.println("\n1- Corrente  para corrente");
							System.out.println("2- Corrente para poupança");
							System.out.println("3- poupanca  para corrente");
							System.out.println("4- poupança para poupança");
							System.out.print("\nSelecionar opção: ");
							tipo = input.nextInt();

							// System.out.println("Valor: ");
							// valor = input.nextDouble();

							System.out.print("\nConta destino: ");
							input.nextLine();

							int idDestino = input.nextInt();

							input.nextLine();
							System.out.println("\nConfirma a operação ? [S] [N]");
							String op = input.nextLine();

							int result = 0;
							saldos = conta.saldo(cpfCliente);

							if (op.equalsIgnoreCase("s")) {

								if (tipo == 1 && saldos[0] >= valor)
									result = conta.transferirEntreClientes(cpfCliente, 1, 1, valor, idDestino);
								if (tipo == 2 && saldos[0] >= valor)
									result = conta.transferirEntreClientes(cpfCliente, 1, 2, valor, idDestino);
								if (tipo == 3 && saldos[1] >= valor)
									result = conta.transferirEntreClientes(cpfCliente, 2, 1, valor, idDestino);
								if (tipo == 4 && saldos[1] >= valor)
									result = conta.transferirEntreClientes(cpfCliente, 2, 2, valor, idDestino);

							}

							if (result == 1) {
								System.out.println("OPERAÇÃO REALIZADA COM SUCESSO");
							} else
								System.out.println("OPERAÇÃO NÃO FOI REALIZADA. VERIFIQUE OS DADOS");

						}

					}

					opc = menu();

					break;

				// saque
				case 3:
					saldos = conta.saldo(cpfCliente);
					System.out.println("\n\tSAQUE");
					System.out.println("=========================================");

					System.out.println("\nSALDO CONTA CORRENTE " + saldos[0]);
					System.out.println("SALDO CONTA POUPANÇA " + saldos[1]);
					System.out.println("SALDO RENDA FIXA " + saldos[2]);

					System.out.println("\n1- CONTA CORRENTE");
					System.out.println("2- CONTA POUPANÇA");
					if (investimento)
						System.out.println("3- RENDA FIXA");

					System.out.print("\nSELECIONE A OPÇÃO: ");
					tipo = input.nextInt();

					System.out.print("\nVALOR: ");
					valor = input.nextDouble();

					boolean saque = conta.sacar(tipo, valor, cpfCliente);

					if (saque) {
						System.out.println("\nSaque Realizado com sucesso!");
					} else {
						System.out.println("\nSaldo não disponível");
					}

					opc = menu();
					break;

				// saldo
				case 4:
					System.out.println();
					saldos = conta.saldo(cpfCliente);
					System.out.println("\tSALDO");
					System.out.println("=========================================");
					System.out.println("CONTA CORRENTE  :  " + saldos[0]);
					System.out.println("CONTA POUPANÇA  :  " + saldos[1]);
					System.out.println("RENDA FIXA      :  " + saldos[2]);
					opc = menu();

					break;
				case 5:
					System.out.println("\n\tINVESTIMENTO EM CONTA FIXA");
					System.out.println("=========================================\n");

					if (!investimento) {
						System.out.print("\nDeseja investir na renda fixa ? [S] [N]");
						input.reset();
						String investir = input.nextLine();

						investimento = investir.equalsIgnoreCase("s") ? true : false;
						conta.setInvestimento(cpfCliente);
					}
					opc = menu();

					break;
				// projeção do investimento
				case 6:

					System.out.println("\n\tPROJEÇÃO DA RENDA FIXA");
					System.out.println("=========================================\n");

					System.out.println("1-POUPANÇA");
					if (investimento)
						System.out.println("2-RENDA FIXA");

					System.out.print("\nSELECIONE A OPÇÃO: ");
					opc = input.nextInt();

					double[] valores = conta.projecaoInvestimento(opc, cpfCliente);
					System.out.println();

					System.out.println("PROJEÇÃO DO INVESTIMENTO 3 MESES  : " + df.format(valores[0]));
					System.out.println("PROJEÇÃO DO INVESTIMENTO 6 MESES  : " + df.format(valores[1]));
					System.out.println("PROJEÇÃO DO INVESTIMENTO 12 MESES : " + df.format(valores[2]));

					input.nextLine();
					opc = menu();
					break;

				default:
					opc = menu();

					break;
				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
