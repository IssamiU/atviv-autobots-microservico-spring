package com.autobots.automanager;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AutomanagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (repositorioEmpresa.count() > 0) {
            System.out.println("Banco de dados já contém dados. Pulando inserção inicial.");
            return;
        }

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Car service toyota ltda");
        empresa.setNomeFantasia("Car service manutenção veicular");
        empresa.setCadastro(new Date());

        Endereco enderecoEmpresa = new Endereco();
        enderecoEmpresa.setEstado("São Paulo");
        enderecoEmpresa.setCidade("São Paulo");
        enderecoEmpresa.setBairro("Centro");
        enderecoEmpresa.setRua("Av. São João");
        enderecoEmpresa.setNumero("00");
        enderecoEmpresa.setCodigoPostal("01035-000");
        empresa.setEndereco(enderecoEmpresa);

        Telefone telefoneEmpresa = new Telefone();
        telefoneEmpresa.setDdd("011");
        telefoneEmpresa.setNumero("986454527");
        empresa.getTelefones().add(telefoneEmpresa);

        // administrador
        Usuario admin = new Usuario();
        admin.setNome("Administrador do Sistema");
        admin.setNomeSocial("Admin");
        admin.getPerfis().add(PerfilUsuario.ADMINISTRADOR);
        admin.setDataNascimento(criarData(1990, 1, 1));

        Email emailAdmin = new Email();
        emailAdmin.setEndereco("admin@admin.com");
        admin.getEmails().add(emailAdmin);

        CredencialUsuarioSenha credencialAdmin = new CredencialUsuarioSenha();
        credencialAdmin.setInativo(false);
        credencialAdmin.setNomeUsuario("admin");
        credencialAdmin.setSenha(passwordEncoder.encode("admin123"));
        credencialAdmin.setCriacao(new Date());
        credencialAdmin.setUltimoAcesso(new Date());
        admin.getCredenciais().add(credencialAdmin);

        empresa.getUsuarios().add(admin);

        // gerente
        Usuario gerente = new Usuario();
        gerente.setNome("Gerente da Loja");
        gerente.setNomeSocial("Gerente");
        gerente.getPerfis().add(PerfilUsuario.GERENTE);
        gerente.setDataNascimento(criarData(1985, 5, 15));

        Email emailGerente = new Email();
        emailGerente.setEndereco("gerente@gerente.com");
        gerente.getEmails().add(emailGerente);

        CredencialUsuarioSenha credencialGerente = new CredencialUsuarioSenha();
        credencialGerente.setInativo(false);
        credencialGerente.setNomeUsuario("gerente");
        credencialGerente.setSenha(passwordEncoder.encode("gerente123"));
        credencialGerente.setCriacao(new Date());
        credencialGerente.setUltimoAcesso(new Date());
        gerente.getCredenciais().add(credencialGerente);

        empresa.getUsuarios().add(gerente);

        // funcionario
        Usuario funcionario = new Usuario();
        funcionario.setNome("Pedro Alcântara de Bragança e Bourbon");
        funcionario.setNomeSocial("Dom Pedro");
        funcionario.getPerfis().add(PerfilUsuario.VENDEDOR);
        funcionario.setDataNascimento(criarData(1798, 10, 12)); 

        Email emailFuncionario = new Email();
        emailFuncionario.setEndereco("vendedor@vendedor.com");
        funcionario.getEmails().add(emailFuncionario);

        Endereco enderecoFuncionario = new Endereco();
        enderecoFuncionario.setEstado("São Paulo");
        enderecoFuncionario.setCidade("São Paulo");
        enderecoFuncionario.setBairro("Jardins");
        enderecoFuncionario.setRua("Av. São Gabriel");
        enderecoFuncionario.setNumero("00");
        enderecoFuncionario.setCodigoPostal("01435-001");
        funcionario.setEndereco(enderecoFuncionario);

        Telefone telefoneFuncionario = new Telefone();
        telefoneFuncionario.setDdd("011");
        telefoneFuncionario.setNumero("9854633728");
        funcionario.getTelefones().add(telefoneFuncionario);

        Documento cpfFunc = new Documento();
        cpfFunc.setDataEmissao(new Date());
        cpfFunc.setNumero("856473819229");
        cpfFunc.setTipo(TipoDocumento.CPF);
        funcionario.getDocumentos().add(cpfFunc);

        CredencialUsuarioSenha credencialFuncionario = new CredencialUsuarioSenha();
        credencialFuncionario.setInativo(false);
        credencialFuncionario.setNomeUsuario("vendedor");
        credencialFuncionario.setSenha(passwordEncoder.encode("vendedor123"));
        credencialFuncionario.setCriacao(new Date());
        credencialFuncionario.setUltimoAcesso(new Date());
        funcionario.getCredenciais().add(credencialFuncionario);

        empresa.getUsuarios().add(funcionario);

        // cliente
        Usuario cliente = new Usuario();
        cliente.setNome("Issami Umeoka da Silva Pinto");
        cliente.setNomeSocial("Pinto Silva");
        cliente.getPerfis().add(PerfilUsuario.CLIENTE);
        cliente.setDataNascimento(criarData(1825, 12, 2)); 

        Email emailCliente = new Email();
        emailCliente.setEndereco("cliente@cliente.com");
        cliente.getEmails().add(emailCliente);

        Documento cpfCliente = new Documento();
        cpfCliente.setDataEmissao(new Date());
        cpfCliente.setNumero("12584698533");
        cpfCliente.setTipo(TipoDocumento.CPF);
        cliente.getDocumentos().add(cpfCliente);

        CredencialUsuarioSenha credencialCliente = new CredencialUsuarioSenha();
        credencialCliente.setInativo(false);
        credencialCliente.setNomeUsuario("cliente");
        credencialCliente.setSenha(passwordEncoder.encode("cliente123"));
        credencialCliente.setCriacao(new Date());
        credencialCliente.setUltimoAcesso(new Date());
        cliente.getCredenciais().add(credencialCliente);

        Endereco enderecoCliente = new Endereco();
        enderecoCliente.setEstado("São Paulo");
        enderecoCliente.setCidade("São José dos Campos");
        enderecoCliente.setBairro("Centro");
        enderecoCliente.setRua("Av. Dr. Nelson D'Ávila");
        enderecoCliente.setNumero("00");
        enderecoCliente.setCodigoPostal("12245-070");
        cliente.setEndereco(enderecoCliente);
        
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC-0000");
        veiculo.setModelo("corolla-cross");
        veiculo.setTipo(TipoVeiculo.SUV);
        veiculo.setProprietario(cliente);
        cliente.getVeiculos().add(veiculo);
        
        empresa.getUsuarios().add(cliente);

        Mercadoria rodaLigaLeve = new Mercadoria();
        rodaLigaLeve.setCadastro(new Date());
        rodaLigaLeve.setFabricao(new Date());
        rodaLigaLeve.setNome("Roda de liga leva modelo toyota etios");
        rodaLigaLeve.setValidade(new Date());
        rodaLigaLeve.setQuantidade(30L);
        rodaLigaLeve.setValor(300.0);
        rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyota para modelos do tipo hatch");
        empresa.getMercadorias().add(rodaLigaLeve);

        Servico trocaRodas = new Servico();
        trocaRodas.setDescricao("Troca das rodas do carro por novas");
        trocaRodas.setNome("Troca de rodas");
        trocaRodas.setValor(50.0); 

        Servico alinhamento = new Servico();
        alinhamento.setDescricao("Alinhamento das rodas do carro");
        alinhamento.setNome("Alinhamento de rodas");
        alinhamento.setValor(50.0); 

        empresa.getServicos().add(trocaRodas);
        empresa.getServicos().add(alinhamento);

        Venda venda = new Venda();
        venda.setCadastro(new Date());
        venda.setCliente(cliente);
        venda.getMercadorias().add(rodaLigaLeve);
        venda.setIdentificacao("1234698745");
        venda.setFuncionario(funcionario);
        venda.getServicos().add(trocaRodas);
        venda.getServicos().add(alinhamento);
        venda.setVeiculo(veiculo);
        veiculo.getVendas().add(venda);

        empresa.getVendas().add(venda);

        repositorioEmpresa.save(empresa);
        
        System.out.println("====================================");
        System.out.println("Dados iniciais inseridos com sucesso!");
        System.out.println("Usuários criados:");
        System.out.println("Admin: username=admin, senha=admin123");
        System.out.println("Gerente: username=gerente, senha=gerente123");
        System.out.println("Vendedor: username=vendedor, senha=vendedor123");
        System.out.println("Cliente: username=cliente, senha=cliente123");
        System.out.println("====================================");
    }
    
    private Date criarData(int ano, int mes, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia); 
        return cal.getTime();
    }
}