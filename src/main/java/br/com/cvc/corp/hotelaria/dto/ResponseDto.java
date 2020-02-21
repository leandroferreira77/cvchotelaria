package br.com.cvc.corp.hotelaria.dto;

import static java.util.Objects.isNull;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("responseDefault")
public class ResponseDto extends BaseDto {

    private static final long serialVersionUID = 7180897155494459587L;

    private Integer codigo;
    private String mensagem;
    private List<DetalheErroDto> erros;

    public ResponseDto(Integer codigo, String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public ResponseDto() {
        super();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(final Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(final String mensagem) {
        this.mensagem = mensagem;
    }

    public List<DetalheErroDto> getErros() {
        if (isNull(erros)) {
            erros = new ArrayList<>();
        }
        return erros;
    }

    public void setErros(final List<DetalheErroDto> erros) {
        this.erros = erros;
    }

    @Override
    public String toString() {
        return "ResponseDto [codigo=" + codigo + ", mensagem=" + mensagem + ", erros=" + erros + "]";
    }
}
