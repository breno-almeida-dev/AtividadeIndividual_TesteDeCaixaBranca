/*
 * Pacote da classe, define que esse código pertence ao
 * pacote login.
 */
package login;

/*
 * Aqui são realizados alguns imports necessários para
 * o funcionamento do JDBC.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * Declara a classe pública User, que está realizando a
 * conexão com o banco de dados.
 */
public class User {
	/*
	 * Nesse bloco, o método é responsável por fazer a conexão
	 * com o banco de dados e realizar um retorno. 
	 */
	public Connection conectarBD() {
		Connection conn = null;
		/*
		 * Dentro do bloco try-catch acontece a chamada para carregar
		 * o driver do JDBC, porém há erros na digitação do caminho
		 * para o driver, resultando assim o retorno do Exception.
		 * 
		 * Além disso, a string que abriga a url de conexão com o JDBC
		 * está passando o usuário e senha diretamente dentro dela, que
		 * é um grande erro de segurança.
		 */
		try {
			Class.forName("com.mysql.Driver.Manager").newInstance();
			String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
			conn = DriverManager.getConnection(url);
		}catch (Exception e) { }
		return conn;}
	
	/*
	 * Antes de entrar no método de verificar o usuário, é realizado
	 * a instância de dois atributos, nome que guarda o nome do usuário
	 * e do result que confere se o login foi bem sucedido, porém ambos
	 * não possuem getters/setters para proteção, ambos podem ser facilmente
	 * modificados por classes externas diretamente.
	 * 
	 * No método verificarUsuario é esperado dois parâmetros para funcionamento,
	 * login e senha, para conferir se há um usuário, e retorna um boolean
	 * para indicar o sucesso.
	 * 
	 * Dentro do método é declarada uma string vazia chamada sql e pede uma
	 * conexão ao método conectarBD, mas sem verificar se conn é null, para conferir
	 * se há algum objeto na memória.
	 */
	public String nome="";
	public boolean result = false;
	public boolean verificarUsuario(String login, String senha) {
		String sql = "";
		Connection conn = conectarBD();
		/*
		 * Logo em seguida é construido uma consulta no SQL com uma
		 * string concatenada, gerando uma grande brecha para ataques
		 * com comandos SQL (SQL Injection), já que não há qualquer tipo de proteção
		 * para validação de dados,
		 */
		//INSTALAÇÃO SQL
		sql += "select nome from usuarios ";
		sql +="where login = " + "'" + login + "'";
		sql += " and senha = " + "'" + senha + "';";
		/*
		 * Dentro desse bloco try-catch, persiste o erro de conexão com o uso
		 * de comandos frágeis e inadequados, deixando brechas de segurança, como
		 * a falta do uso de PreparedStatement e falta de comandos para fechar a
		 * conexão.
		 * 
		 * Ainda dentro do bloco há uma condição que confere se a conexão obteve sucesso
		 * e altera os valores das variáveis. Logo após isso 
		 */
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				result = true;
				nome = rs.getString("nome");}
		}catch (Exception e) {   }
		return result; 	}
	}//fim da classe
