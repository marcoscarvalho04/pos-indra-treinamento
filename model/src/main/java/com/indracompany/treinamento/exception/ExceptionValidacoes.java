package com.indracompany.treinamento.exception;

import com.indracompany.treinamento.util.FacesUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.core.Response;

/**
 *
 * @author efmendes
 *
 */

@AllArgsConstructor
public enum ExceptionValidacoes implements AplicacaoExceptionValidacoes {



  // Mensagens de Erro
  ERRO_ACESSO_SISTEMA("msg.app.erro.acesso.sistema", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.INTERNAL_SERVER_ERROR),
  ERRO_CAMPO_OBRIGATORIO("msg.app.erro.campo.obrigatorio", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO, HttpStatus.BAD_REQUEST),
  ERRO_EXCLUSAO_GENERICO("msg.app.erro.exclusao.generico", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO, HttpStatus.INTERNAL_SERVER_ERROR),
  ERRO_OBJETO_NAO_ENCONTRADO("msg.app.erro.objeto.nao.encontrado", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO, HttpStatus.NOT_FOUND),
  ERRO_VALIDACAO("msg.app.erro.validacao", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_SERIALIZAR_JSON("msg.app.erro.serializar.json", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_ACESSO_NEGADO_JIRA("msg.erro.acesso.negado.jira", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.INTERNAL_SERVER_ERROR),
  ERRO_LOGIN_SENHA_INVALIDO("msg.erro.login.senha.invalido", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_CPF_INVALIDO("msg.erro.cpf.invalido", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_CONTA_INVALIDA("msg.erro.conta.invalida", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_SALDO_INEXISTENTE("msg.erro.saldo.inexistente", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_CONTA_DUPLICADA("msg.erro.conta.duplicada", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_DEPOSITO_INVALIDO("msg.app.erro.acesso.deposito", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_SAQUE_SALDO_INSUFICIENTE("msg.app.erro.acesso.saque.insuficiente", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),
  ERRO_SAQUE_INVALIDO("msg.app.erro.acesso.saque.invalido", null, AplicacaoExceptionValidacoes.SEVERIDADE_ERRO,HttpStatus.BAD_REQUEST),




  // Mensagens Alterta
  ALERTA_NENHUM_REGISTRO_ENCONTRADO("msg.app.alerta.nenhum.registro.encontrado", null, AplicacaoExceptionValidacoes.SEVERIDADE_ALERTA,HttpStatus.NOT_FOUND),;


  @Getter
  @Setter
  private String codigoMsg;

  @Getter
  @Setter
  private String codigoMsgAuxiliar;

  @Getter
  @Setter
  private Integer severidade = AplicacaoExceptionValidacoes.SEVERIDADE_ERRO;

  @Getter
  @Setter
  public HttpStatus resultStatus;

  public static ExceptionValidacoes carregarPorCodigoMsg(final String codigo) {
    for (final ExceptionValidacoes co : ExceptionValidacoes.values()) {
      if (codigo.equals(co.getCodigoMsg())) {
        return co;
      }
    }
    return null;
  }

  @Override
  public String getDescricaoMsg(final String... params) {
    return FacesUtil.obterTextoMessagesProperties(getCodigoMsg(), params);
  }

  @Override
  public String getDescricaoMsgAuxiliar(final String... params) {
    return FacesUtil.obterTextoMessagesProperties(getCodigoMsgAuxiliar(), params);
  }

  @Override
  public ResponseEntity responseStatus() {
    return new ResponseEntity(this.getDescricaoMsg(this.getCodigoMsg()),this.getResultStatus());
  }

}
